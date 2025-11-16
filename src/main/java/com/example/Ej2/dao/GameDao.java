package com.example.Ej2.dao;

import com.example.Ej1.dao.GenericDao;
import com.example.Ej2.domain.Game;
import com.example.Ej2.dto.TopGameDto;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface GameDao extends GenericDao<Game, Long> {

    List<TopGameDto> topGamesByMatches(Session session, LocalDateTime start, LocalDateTime end, int limit);
}
