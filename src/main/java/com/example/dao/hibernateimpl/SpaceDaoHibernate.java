package com.example.dao.hibernateimpl;

import com.example.dao.SpaceDao;
import com.example.dao.VenueDao;
import com.example.domain.Space;
import com.example.domain.Venue;

public class SpaceDaoHibernate extends GenericDaoHibernate<Space, Long> implements SpaceDao {

    public SpaceDaoHibernate() {
        super(Space.class);
    }
}
