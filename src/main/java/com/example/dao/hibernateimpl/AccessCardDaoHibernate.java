package com.example.dao.hibernateimpl;

import com.example.dao.AccessCardDao;
import com.example.domain.AccessCard;

public class AccessCardDaoHibernate extends GenericDaoHibernate<AccessCard, Long> implements AccessCardDao {

    public AccessCardDaoHibernate() {
        super(AccessCard.class);
    }
}
