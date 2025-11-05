package com.example.dao.hibernateimpl;

import com.example.dao.VenueDao;
import com.example.domain.Venue;

public class VenueDaoHibernate extends GenericDaoHibernate<Venue, Long> implements VenueDao {

    public VenueDaoHibernate() {
        super(Venue.class);
    }
}
