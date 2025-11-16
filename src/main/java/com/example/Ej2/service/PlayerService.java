package com.example.Ej2.service;

import com.example.Ej1.domain.Venue;
import com.example.Ej2.config.HibernateUtil;
import com.example.Ej2.dao.PlayerDao;
import com.example.Ej2.dao.RfidCardDao;
import com.example.Ej2.dao.hibernateimpl.PlayerDaoHibernate;
import com.example.Ej2.dao.hibernateimpl.RfidCardDaoHibernate;
import com.example.Ej2.domain.Match;
import com.example.Ej2.domain.Player;
import com.example.Ej2.domain.RfidCard;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;

import java.time.LocalDateTime;
import java.util.List;

public class PlayerService {
    private final SessionFactory sf;
    private final PlayerDao playerDao;
    private final RfidCardDao rfidCardDao;

    public PlayerService(){
        sf = HibernateUtil.getSessionFactory();
        playerDao = new PlayerDaoHibernate();
        rfidCardDao = new RfidCardDaoHibernate();
    }

    public Long create(Player player) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            playerDao.create(s, player);
            tx.commit();
            return player.getId();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Player> findPlayersWithInactiveCardAndRecentMatches(LocalDateTime sinceDate){
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            Root<Match> match = cq.from(Match.class);

            Join<Match, Player> player = match.join("player");
            Join<Player, RfidCard> card = player.join("rfidCard");

            cq.select(player).distinct(true)
                    .where(cb.and(
                            cb.isFalse(card.get("active")),
                            cb.greaterThanOrEqualTo(match.get("startedAt"), sinceDate)
                    ));

            return s.createQuery(cq).getResultList();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Player findById(Long id) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Player player  = playerDao.findById(s, id);
            tx.commit();
            return player;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Player> findAll() {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Player> players = playerDao.findAll(s);
            tx.commit();
            return players;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void update(Player player) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            playerDao.update(s, player);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(Player player) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            playerDao.delete(s, player);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void deleteById(Long id) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            playerDao.deleteById(s, id);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Long emitirRfidCard(Long playerId, String uid){
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Player player = playerDao.findById(s, playerId);
            if (player == null)
                throw new IllegalArgumentException("Jugador no encontrado con esa ID");

            if (player.getRfidCard() != null) {
                System.out.println("El jugador ya tenía una tarjeta emitida.");
                return player.getRfidCard().getId();
            }

            RfidCard card = new RfidCard();
            card.setUid(uid);
            card.setIssuedAt(LocalDateTime.now());
            card.setActive(true);
            card.setPlayer(player);

            Long cardId = rfidCardDao.create(s, card);
            tx.commit();
            return cardId;

        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void cambiarEstadoTarjeta(Long cardId, boolean activa){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            RfidCard card = rfidCardDao.findById(s, cardId);
            if (card == null){
                throw new IllegalArgumentException("Tarjeta no encontrada con esa ID");
            }

            card.setActive(activa);
            rfidCardDao.update(s, card);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void cambiarUid(Long cardId, String nuevoUid){
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            RfidCard card = rfidCardDao.findById(s, cardId);
            if (card == null){
                throw new IllegalArgumentException("Tarjeta no encontrada con esa Id");
            }

            card.setUid(nuevoUid);
            rfidCardDao.update(s, card);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void retirarTarjeta(Long playerId){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Player player = playerDao.findById(s, playerId);
            if (player == null || player.getRfidCard() == null){
                throw new IllegalStateException("El jugador no tiene tarjeta asignada.");
            }

            RfidCard card = player.getRfidCard();
            player.setRfidCard(null);
            playerDao.update(s, player);

            rfidCardDao.delete(s, card);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }


}
