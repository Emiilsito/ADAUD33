package com.example.Ej1.dao;

import com.example.Ej1.domain.Venue;
import com.example.Ej1.dto.CitySpacesCount;
import com.example.Ej1.dto.ConfirmedVenuesWithProfit;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface VenueDao extends GenericDao<Venue, Long>{

    List<CitySpacesCount> top5cityWithMoreSpaces(Session session);

    List<ConfirmedVenuesWithProfit> getRevenueByVenue(Session session, LocalDateTime start, LocalDateTime end);
}
