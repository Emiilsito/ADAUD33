package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.VenueDao;
import com.example.Ej1.domain.Venue;

public class VenueDaoHibernate extends GenericDaoHibernate<Venue, Long> implements VenueDao {

    public VenueDaoHibernate() {
        super(Venue.class);
    }
}
