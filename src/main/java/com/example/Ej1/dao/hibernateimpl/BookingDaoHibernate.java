package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.BookingDao;
import com.example.Ej1.domain.Booking;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDaoHibernate extends GenericDaoHibernate<Booking, Long> implements BookingDao {

    public BookingDaoHibernate() {
        super(Booking.class);
    }

    @Override
    public List<Booking> getConfirmedByVenueAndRange(Session session, String venueName, LocalDateTime startDate, LocalDateTime endTime) {
        return session.createNamedQuery("Booking.confirmedVenueRange", Booking.class)
                .setParameter("venueName", venueName)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endTime)
                .getResultList();
    }
}
