package com.example.Ej1.service;

import com.example.Ej1.config.HibernateUtil;
import com.example.Ej1.dao.SpaceDao;
import com.example.Ej1.dao.VenueDao;
import com.example.Ej1.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.Ej1.dao.hibernateimpl.VenueDaoHibernate;
import com.example.Ej1.domain.Booking;
import com.example.Ej1.domain.Space;
import com.example.Ej1.domain.Tag;
import com.example.Ej1.domain.Venue;
import com.example.Ej1.dto.MostProfitSpacesDto;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    /*
        4. Espacios activos por capacidad minima y precio maximo
     */

    public List<Space> findActiveSpaces(int minCapacity, double maxPrice){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Space> cq = cb.createQuery(Space.class);
            Root<Space> root = cq.from(Space.class);

            cq.select(root)
                    .where(cb.and(
                            cb.isTrue(root.get("active")),
                            cb.ge(root.get("capacity"), minCapacity),
                            cb.le(root.get("hourlyPrice"), BigDecimal.valueOf(maxPrice))
                    ))
                    .orderBy(cb.asc(root.get("capacity")), cb.asc(root.get("hourlyPrice")));


            return s.createQuery(cq).getResultList();

        } catch (PersistenceException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /*
        5. Espacios nunca reservados
     */

    public List<Space> getNeverReservedSpaces(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Space> spaces = spaceDao.getNeverReservedSpaces(s);
            tx.commit();
            return spaces;
        } catch (PersistenceException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    /*
        7. Top 3 espacios por ingresos confirmados
     */

    public List<MostProfitSpacesDto> getMostProfitSpaces(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            List<MostProfitSpacesDto> list = spaceDao.findTop3MostProfitSpaces(s);
            tx.commit();
            return list;
        } catch (PersistenceException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /*
    CRITERIA
     */

    public void testCriteria(){
        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder(); //Construye expresiones
            CriteriaQuery<Space> cq = cb.createQuery(Space.class);
            Root<Space> root = cq.from(Space.class);
            Join<Space, Venue> joinVenue = root.join("venue");

            //Todos los espacios activos
            cq.select(root)
                            .where(cb.and(cb.gt(root.get("capacity"), 10),
                                    cb.equal(root.get("name"), "")),
                                    cb.isTrue(root.get("active"))
                            )
                    .having()
                                    .orderBy(cb.asc(root.get("name")),
                                            cb.desc(root.get("")));

            List<Space> spaces = session.createQuery(cq).getResultList();


            tx.commit();
        } catch (PersistenceException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /*
    Devuelve todos los espacios cuyo nombre contenga ELX
    y que tienen al menos un Tag llamado wifi
     */

    public void getSpaceElxOnlyOneTagWifi(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Space> cq = cb.createQuery(Space.class);
            Root<Space> root = cq.from(Space.class);
            Join<Space, Tag> joinTag = root.join("tags", JoinType.LEFT);

            cq.select(root)
                            .where(cb.and(cb.like(root.get("name"), "%ELX%"),
                                    cb.equal(cb.lower(joinTag.get("name")), "wifi")));

            List<Space> spaces = s.createQuery(cq).getResultList();

            tx.commit();
        } catch (PersistenceException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }


    /*
    Queremos una lista de espacios que están activos, tienen una capacidad minima y un precio maximo por hora,
    ordenados por precio ascendente y por capacidad desc.
     */

    public void getListSpacesActive(int capacidad, BigDecimal precioMax){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Space> cq = cb.createQuery(Space.class);
            Root<Space> root = cq.from(Space.class);

            cq.select(root)
                    .where(cb.and(
                            cb.isTrue(root.get("active"))),
                            cb.greaterThan(root.get("capacity"), capacidad),
                            cb.lessThanOrEqualTo(root.get("hourlyPrice"), precioMax))
                    .orderBy(cb.asc(root.get("hourlyPrice")))
                    .orderBy(cb.desc(root.get("capacity")));

            List<Space> spaces = s.createQuery(cq).getResultList();

            tx.commit();

        } catch (PersistenceException e){
            if (tx != null) tx.rollback();
            throw e;
        }
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
