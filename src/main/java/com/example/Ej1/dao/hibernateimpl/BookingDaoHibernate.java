package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.BookingDao;
import com.example.Ej1.domain.Booking;

public class BookingDaoHibernate extends GenericDaoHibernate<Booking, Long> implements BookingDao {

    public BookingDaoHibernate() {
        super(Booking.class);
    }
}
