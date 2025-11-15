package com.example.Ej1.service;

import com.example.Ej1.config.HibernateUtil;
import com.example.Ej1.dao.BookingDao;
import com.example.Ej1.dao.SpaceDao;
import com.example.Ej1.dao.UserDao;
import com.example.Ej1.dao.hibernateimpl.BookingDaoHibernate;
import com.example.Ej1.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.Ej1.dao.hibernateimpl.UserDaoHibernate;
import com.example.Ej1.domain.Booking;
import com.example.Ej1.domain.Space;
import com.example.Ej1.domain.User;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private final SessionFactory sf;
    private final BookingDao bookingDao;
    private final UserDao userDao;
    private final SpaceDao spaceDao;

    public BookingService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.bookingDao = new BookingDaoHibernate();
        this.userDao = new UserDaoHibernate();
        this.spaceDao = new SpaceDaoHibernate();
    }

    /*
        6. Reservas confirmadas por venue y rango.
    */

    public List<Booking> getConfirmedBookingsByVenueAndRange(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Booking> confirmedBookingDtos = bookingDao.getConfirmedByVenueAndRange(s, "Sede Central", LocalDateTime.of(2025, 1, 1, 1, 1), LocalDateTime.of(2025, 12, 12, 1, 1));
            tx.commit();
            return confirmedBookingDtos;
        } catch (PersistenceException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Long create(Long userId, Long spaceId, LocalDateTime start, LocalDateTime end, BigDecimal totalPrice, Booking.BookingStatus status) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            User user = userDao.findById(s, userId);
            Space space = spaceDao.findById(s, spaceId);

            if (user == null) throw new IllegalArgumentException("Usuario con ID " + userId + " no existe.");
            if (space == null) throw new IllegalArgumentException("Espacio con ID " + spaceId + " no existe.");

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setSpace(space);
            booking.setCreatedAt(LocalDateTime.now());
            booking.setStartTime(start);
            booking.setEndTime(end);
            booking.setTotalPrice(totalPrice);
            booking.setStatus(status);

            bookingDao.create(s, booking);

            tx.commit();
            return booking.getId();

        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Booking findById(Long id) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            Booking booking = bookingDao.findById(s, id);
            tx.commit();
            return booking;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Booking> findAll() {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Booking> bookings = bookingDao.findAll(s);
            tx.commit();
            return bookings;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void update(Booking booking) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            bookingDao.update(s, booking);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(Booking booking) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            bookingDao.delete(s, booking);
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
            bookingDao.deleteById(s, id);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }


}
