package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.SpaceDao;
import com.example.Ej1.domain.Space;

public class SpaceDaoHibernate extends GenericDaoHibernate<Space, Long> implements SpaceDao {

    public SpaceDaoHibernate() {
        super(Space.class);
    }
}
