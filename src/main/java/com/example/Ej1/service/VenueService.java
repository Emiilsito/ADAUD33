package com.example.Ej1.service;

import com.example.Ej1.config.HibernateUtil;
import com.example.Ej1.dao.SpaceDao;
import com.example.Ej1.dao.VenueDao;
import com.example.Ej1.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.Ej1.dao.hibernateimpl.VenueDaoHibernate;
import com.example.Ej1.domain.Venue;
import com.example.Ej1.dto.CitySpacesCount;
import com.example.Ej1.dto.ConfirmedVenuesWithProfit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class VenueService {
    private final SessionFactory sf;
    private final VenueDao venueDao;

    public VenueService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.venueDao = new VenueDaoHibernate();
    }

    /*
        TAREA UD3.3 - Consultas avanzadas con Hibernate
     */

    /*
        1. Venues por ciudad
     */

    public List<Venue> getVenuesByCity(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Venue> venuesByCity = s.createNamedQuery("Venue.FindbyCity", Venue.class)
                    .setParameter("city", "Madrid")
                    .getResultList();
            tx.commit();
            return venuesByCity;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    /*
        2. Top 5 ciudades con mas espacios
     */

    public List<CitySpacesCount> getTop5VenuesWithMostSpaces(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<CitySpacesCount> top5Venues = venueDao.top5cityWithMoreSpaces(s);
            tx.commit();
            return top5Venues;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    /*
        3. Venues con ingresos confirmados en rango de fechas.
     */

    public List<ConfirmedVenuesWithProfit> getVenuesConfirmed(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<ConfirmedVenuesWithProfit> venuesWithProfits = venueDao.getRevenueByVenue(s, LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 11, 30, 23, 59));
            tx.commit();
            return venuesWithProfits;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }


    public Long create(Venue venue) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            venueDao.create(s, venue);
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
