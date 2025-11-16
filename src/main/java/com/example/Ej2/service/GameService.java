package com.example.Ej2.service;

import com.example.Ej2.config.HibernateUtil;
import com.example.Ej2.dao.GameDao;
import com.example.Ej2.dao.hibernateimpl.GameDaoHibernate;
import com.example.Ej2.domain.Arcade;
import com.example.Ej2.domain.Game;
import com.example.Ej2.dto.TopGameDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class GameService {
    private final SessionFactory sf;
    private final GameDao gameDao;

    public GameService() {
        gameDao = new GameDaoHibernate();
        sf = HibernateUtil.getSessionFactory();
    }

    /*
        Ejercicio 2
     */

    public List<TopGameDto> getTopGamesByMatches(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<TopGameDto> games = gameDao.topGamesByMatches(s, LocalDateTime.of(2025, 1, 1, 1, 1), LocalDateTime.of(2025, 12, 12, 1, 1), 3);
            tx.commit();
            return games;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
