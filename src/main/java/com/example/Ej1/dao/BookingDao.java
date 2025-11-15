package com.example.Ej1.dao;

import com.example.Ej1.domain.Booking;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDao extends GenericDao<Booking, Long>{

    List<Booking> getConfirmedByVenueAndRange(Session session, String venueName, LocalDateTime startDate, LocalDateTime endTime);

}
