package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej1.domain.AccessCard;
import com.example.Ej2.dao.ArcadeDao;
import com.example.Ej2.domain.Arcade;

public class ArcadeDaoHibernate extends GenericDaoHibernate<Arcade, Long> implements ArcadeDao {
    public ArcadeDaoHibernate() {
        super(Arcade.class);
    }
}
