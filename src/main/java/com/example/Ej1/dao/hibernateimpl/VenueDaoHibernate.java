package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.VenueDao;
import com.example.Ej1.domain.Venue;
import com.example.Ej1.dto.CitySpacesCount;
import com.example.Ej1.dto.ConfirmedVenuesWithProfit;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public class VenueDaoHibernate extends GenericDaoHibernate<Venue, Long> implements VenueDao {

    public VenueDaoHibernate() {
        super(Venue.class);
    }

    @Override
    public List<CitySpacesCount> top5cityWithMoreSpaces(Session session) {
        String query =
                "SELECT new com.example.Ej1.dto.CitySpacesCount(v.city, COUNT(s)) " +
                        "FROM Venue v JOIN v.spaces s " +
                        "GROUP BY v.city " +
                        "ORDER BY COUNT(s) DESC";

        return session.createQuery(query, CitySpacesCount.class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public List<ConfirmedVenuesWithProfit> getRevenueByVenue(Session session, LocalDateTime start, LocalDateTime end) {
        String query = "SELECT new com.example.Ej1.dto.ConfirmedVenuesWithProfit(v.name, SUM(b.totalPrice) * 1.0) " +
                "FROM Booking b " +
                "JOIN b.space s " +
                "JOIN s.venue v " +
                "WHERE b.status = com.example.Ej1.domain.Booking.BookingStatus.CONFIRMED " +
                "AND b.startTime >= :startDate " +
                "AND b.endTime <= :endDate " +
                "GROUP BY v.name " +
                "ORDER BY SUM(b.totalPrice) DESC";

        return session.createQuery(query, ConfirmedVenuesWithProfit.class)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getResultList();
    }

}
