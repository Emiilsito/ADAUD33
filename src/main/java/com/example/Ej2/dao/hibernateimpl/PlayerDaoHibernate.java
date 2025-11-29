package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.PlayerDao;
import com.example.Ej2.domain.Player;
import org.hibernate.Session;

import java.util.List;

public class PlayerDaoHibernate extends GenericDaoHibernate<Player, Long> implements PlayerDao {
    public PlayerDaoHibernate() {
        super(Player.class);
    }

    @Override
    public List<Player> getPlayersWithAtLeastNAchievements(Session session, int minAchievements) {
        String query = """
                SELECT p
                FROM Player p
                JOIN p.achievements a
                GROUP BY p
                HAVING COUNT(a) >= :minAchievements
                """;

        return session.createQuery(query, Player.class)
                .setParameter("minAchievements", minAchievements)
                .getResultList();
    }
}
