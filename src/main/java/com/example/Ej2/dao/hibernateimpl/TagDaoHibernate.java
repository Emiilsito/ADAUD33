package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.TagDao;
import com.example.Ej2.domain.Tag;

public class TagDaoHibernate extends GenericDaoHibernate<Tag, Long> implements TagDao {
    public TagDaoHibernate() {
        super(Tag.class);
    }
}
