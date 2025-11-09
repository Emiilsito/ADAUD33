package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.MatchDao;
import com.example.Ej2.domain.Match;

public class MatchDaoHibernate extends GenericDaoHibernate<Match, Long> implements MatchDao {
    public MatchDaoHibernate() {
        super(Match.class);
    }
}
