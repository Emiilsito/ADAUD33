package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.SpaceDao;
import com.example.Ej1.domain.Space;
import com.example.Ej1.dto.MostProfitSpacesDto;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public class SpaceDaoHibernate extends GenericDaoHibernate<Space, Long> implements SpaceDao {

    public SpaceDaoHibernate() {
        super(Space.class);
    }

    @Override
    public List<MostProfitSpacesDto> findTop3MostProfitSpaces(Session session) {
        String query = "select new com.example.Ej1.dto.MostProfitSpacesDto(b.space.code, b.space.name, sum(b.totalPrice)) " +
                "from Booking b " +
                "where b.status = com.example.Ej1.domain.Booking.BookingStatus.CONFIRMED " +
                "group by b.space.code, b.space.name " +
                "order by sum(b.totalPrice) desc ";
        return session.createQuery(query, MostProfitSpacesDto.class)
                .setMaxResults(3)
                .getResultList();
    }

    @Override
    public List<Space> getNeverReservedSpaces(Session session){
        String query = "SELECT s.* FROM spaces s LEFT JOIN bookings b ON s.id = b.space_id" +
                " where b.space_id is null";
        return session.createNativeQuery(query, Space.class)
                .getResultList();
    }
}
