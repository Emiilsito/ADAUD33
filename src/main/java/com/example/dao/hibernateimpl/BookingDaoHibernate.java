package com.example.dao.hibernateimpl;

import com.example.dao.BookingDao;
import com.example.domain.Booking;

public class BookingDaoHibernate extends GenericDaoHibernate<Booking, Long> implements BookingDao {

    public BookingDaoHibernate() {
        super(Booking.class);
    }
}
