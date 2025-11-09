package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.CabinetDao;
import com.example.Ej2.domain.Cabinet;

public class CabinetDaoHibernate extends GenericDaoHibernate<Cabinet, Long> implements CabinetDao {
    public CabinetDaoHibernate() {
        super(Cabinet.class);
    }
}
