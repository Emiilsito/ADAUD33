package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.GameDao;
import com.example.Ej2.domain.Game;
import com.example.Ej2.dto.TopGameDto;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public class GameDaoHibernate extends GenericDaoHibernate<Game, Long> implements GameDao {
    public GameDaoHibernate() {
        super(Game.class);
    }

    @Override
    public List<TopGameDto> topGamesByMatches(Session session, LocalDateTime start, LocalDateTime end, int limit) {
        String query = """
                SELECT new com.example.Ej2.dto.TopGameDto(g.name, COUNT(m.id))
                FROM Match m
                JOIN m.cabinet c
                JOIN c.game g
                WHERE m.startedAt BETWEEN :startDate AND :endDate
                GROUP BY g.id, g.name
                ORDER BY COUNT(m.id) DESC
                """;
        return session.createQuery(query, TopGameDto.class)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .setMaxResults(limit)
                .getResultList();
    }
}
