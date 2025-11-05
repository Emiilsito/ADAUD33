package com.example.dao.hibernateimpl;

import com.example.dao.TagDao;
import com.example.domain.Tag;

public class TagDaoHibernate extends GenericDaoHibernate<Tag, Long> implements TagDao{

    public TagDaoHibernate() {
        super(Tag.class);
    }
}
