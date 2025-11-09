package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.TagDao;
import com.example.Ej1.domain.Tag;

public class TagDaoHibernate extends GenericDaoHibernate<Tag, Long> implements TagDao{

    public TagDaoHibernate() {
        super(Tag.class);
    }
}
