package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.GameDao;
import com.example.Ej2.domain.Game;

public class GameDaoHibernate extends GenericDaoHibernate<Game, Long> implements GameDao {
    public GameDaoHibernate() {
        super(Game.class);
    }
}
