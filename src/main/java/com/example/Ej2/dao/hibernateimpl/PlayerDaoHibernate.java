package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.PlayerDao;
import com.example.Ej2.domain.Player;

public class PlayerDaoHibernate extends GenericDaoHibernate<Player, Long> implements PlayerDao {
    public PlayerDaoHibernate() {
        super(Player.class);
    }
}
