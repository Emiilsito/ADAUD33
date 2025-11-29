package com.example.Ej2.dao;

import com.example.Ej1.dao.GenericDao;
import com.example.Ej2.domain.Player;
import org.hibernate.Session;

import java.util.List;

public interface PlayerDao extends GenericDao<Player, Long> {

    List<Player> getPlayersWithAtLeastNAchievements(Session session, int minAchievements);

}
