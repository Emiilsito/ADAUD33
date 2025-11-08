package com.example.service;

import com.example.config.HibernateUtil;
import com.example.dao.BookingDao;
import com.example.dao.SpaceDao;
import com.example.dao.UserDao;
import com.example.dao.hibernateimpl.BookingDaoHibernate;
import com.example.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.dao.hibernateimpl.UserDaoHibernate;
import com.example.domain.Booking;
import com.example.domain.Space;
import com.example.domain.Tag;
import com.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.awt.print.Book;
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

            bookingDao.save(s, booking);

            tx.commit();
            return booking.getId();

        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Long save(Booking booking){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            bookingDao.save(s, booking);
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
