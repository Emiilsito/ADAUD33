package com.example.Ej2.service;

import com.example.Ej2.config.HibernateUtil;
import com.example.Ej2.dao.ArcadeDao;
import com.example.Ej2.dao.hibernateimpl.ArcadeDaoHibernate;
import com.example.Ej2.domain.Arcade;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ArcadeService {
    private final SessionFactory sf;
    private final ArcadeDao arcadeDao;

    public ArcadeService() {
        arcadeDao = new ArcadeDaoHibernate();
        sf = HibernateUtil.getSessionFactory();
    }

    /*
        Ejercicio 1
     */

    public List<Arcade> getArcadeByName(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Arcade> arcades = arcadeDao.findByName(s, "Center");
            tx.commit();
            return arcades;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
