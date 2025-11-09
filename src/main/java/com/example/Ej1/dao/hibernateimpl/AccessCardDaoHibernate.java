package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.AccessCardDao;
import com.example.Ej1.domain.AccessCard;

public class AccessCardDaoHibernate extends GenericDaoHibernate<AccessCard, Long> implements AccessCardDao {

    public AccessCardDaoHibernate() {
        super(AccessCard.class);
    }
}
