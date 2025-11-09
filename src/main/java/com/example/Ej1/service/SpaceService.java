package com.example.Ej1.service;

import com.example.Ej1.config.HibernateUtil;
import com.example.Ej1.dao.SpaceDao;
import com.example.Ej1.dao.VenueDao;
import com.example.Ej1.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.Ej1.dao.hibernateimpl.VenueDaoHibernate;
import com.example.Ej1.domain.Space;
import com.example.Ej1.domain.Venue;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SpaceService {

    private final SessionFactory sf;
    private final SpaceDao spaceDao;
    private final VenueDao venueDao;

    public SpaceService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.spaceDao = new SpaceDaoHibernate();
        this.venueDao =  new VenueDaoHibernate();
    }

    public Long create(Space space) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            spaceDao.create(s, space);
            tx.commit();
            return space.getId();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Space findById(Long id) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Space space = spaceDao.findById(s, id);
            tx.commit();
            return space;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void update(Space space) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            spaceDao.update(s, space);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(Space space) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            spaceDao.delete(s, space);
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

            spaceDao.deleteById(s, id);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Long createSpace(Long venueId, Space space){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Venue venue = venueDao.findById(s, venueId);
            if (venue == null){
                throw new IllegalArgumentException("El Venue con ID " + venueId + " no existe.");
            }

            space.setVenue(venue);

            spaceDao.create(s, space);
            tx.commit();
            return space.getId();

        } catch (RuntimeException e){
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Space> findAll() {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Space> spaces = spaceDao.findAll(s);
            for (Space space : spaces) {
                if (space.getTags() != null) {
                    space.getTags().size();
                }
            }
            tx.commit();
            return spaces;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

}
