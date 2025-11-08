package com.example.service;

import com.example.config.HibernateUtil;
import com.example.dao.SpaceDao;
import com.example.dao.VenueDao;
import com.example.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.dao.hibernateimpl.VenueDaoHibernate;
import com.example.domain.Space;
import com.example.domain.Venue;
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

    public Long save(Space space) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            spaceDao.save(s, space);
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

            spaceDao.save(s, space);
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
