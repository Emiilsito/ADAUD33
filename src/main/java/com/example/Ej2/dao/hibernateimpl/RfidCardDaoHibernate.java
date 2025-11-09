package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.RfidCardDao;
import com.example.Ej2.domain.RfidCard;

public class RfidCardDaoHibernate extends GenericDaoHibernate<RfidCard, Long> implements RfidCardDao {
    public RfidCardDaoHibernate() {
        super(RfidCard.class);
    }
}
