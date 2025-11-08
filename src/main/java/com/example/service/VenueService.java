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

public class VenueService {
    private final SessionFactory sf;
    private final VenueDao venueDao;
    private final SpaceDao spaceDao;

    public VenueService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.venueDao = new VenueDaoHibernate();
        this.spaceDao = new SpaceDaoHibernate();
    }

    public Long save(Venue venue) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            venueDao.save(s, venue);
            tx.commit();
            return venue.getId();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Venue findById(Long id) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Venue venue = venueDao.findById(s, id);
            tx.commit();
            return venue;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Venue> findAll() {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Venue> venues = venueDao.findAll(s);
            tx.commit();
            return venues;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void update(Venue venue) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            venueDao.update(s, venue);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(Venue venue) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            venueDao.delete(s, venue);
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

            venueDao.deleteById(s, id);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

}
