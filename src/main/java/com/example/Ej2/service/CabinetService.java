package com.example.Ej2.service;

import com.example.Ej2.config.HibernateUtil;
import com.example.Ej2.dao.ArcadeDao;
import com.example.Ej2.dao.CabinetDao;
import com.example.Ej2.dao.GameDao;
import com.example.Ej2.dao.TagDao;
import com.example.Ej2.dao.hibernateimpl.ArcadeDaoHibernate;
import com.example.Ej2.dao.hibernateimpl.CabinetDaoHibernate;
import com.example.Ej2.dao.hibernateimpl.GameDaoHibernate;
import com.example.Ej2.dao.hibernateimpl.TagDaoHibernate;
import com.example.Ej2.domain.Arcade;
import com.example.Ej2.domain.Cabinet;
import com.example.Ej2.domain.Game;
import com.example.Ej2.domain.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CabinetService {
    private final SessionFactory sf;
    private final GameDao gameDao;
    private final ArcadeDao arcadeDao;
    private final TagDao tagDao;
    private final CabinetDao cabinetDao;

    public CabinetService(){
        sf = HibernateUtil.getSessionFactory();
        gameDao = new GameDaoHibernate();
        arcadeDao = new ArcadeDaoHibernate();
        tagDao = new TagDaoHibernate();
        cabinetDao = new CabinetDaoHibernate();
    }

    /*
        4. Cabinets activos por genero
     */

    public List<Cabinet> getCabinetsActiveByGenre(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Cabinet> cabinets = cabinetDao.findActiveByGenre(s, "LUCHA");
            tx.commit();
            return cabinets;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void asignarCabinet(Long cabinetId, Long arcadeId, Long gameId){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Cabinet cabinet = cabinetDao.findById(s, cabinetId);
            Arcade arcade = arcadeDao.findById(s, arcadeId);
            Game game = gameDao.findById(s, gameId);

            if (cabinet == null) {
                throw new IllegalArgumentException("Cabinet no encontrado con ID " + cabinetId);
            }
            if (arcade == null) {
                throw new IllegalArgumentException("Arcade no encontrado con ID " + arcadeId);
            }
            if (game == null) {
                throw new IllegalArgumentException("Juego no encontrado con ID " + gameId);
            }

            cabinet.setArcade(arcade);
            cabinet.setGame(game);
            cabinetDao.update(s, cabinet);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void addTag(Long cabinetId, Long tagId){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Cabinet cabinet = cabinetDao.findById(s, cabinetId);
            Tag tag = tagDao.findById(s, tagId);

            if (cabinet == null) {
                throw new IllegalArgumentException("Cabinet no encontrado con ID " + cabinetId);
            }
            if (tag == null) {
                throw new IllegalArgumentException("Tag no encontrado con ID " + tagId);
            }

            if (!cabinet.getTags().contains(tag)) {
                cabinet.getTags().add(tag);
                cabinetDao.update(s, cabinet);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void removeTag(Long cabinetId, Long tagId){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Cabinet cabinet = cabinetDao.findById(s, cabinetId);
            Tag tag = tagDao.findById(s, tagId);

            if (cabinet == null) {
                throw new IllegalArgumentException("Cabinet no encontrado con ID " + cabinetId);
            }
            if (tag == null) {
                throw new IllegalArgumentException("Tag no encontrado con ID " + tagId);
            }

            if (cabinet.getTags().remove(tag)) {
                cabinetDao.update(s, cabinet);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
