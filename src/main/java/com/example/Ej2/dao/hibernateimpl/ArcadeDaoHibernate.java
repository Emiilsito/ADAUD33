package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej1.domain.AccessCard;
import com.example.Ej2.dao.ArcadeDao;
import com.example.Ej2.domain.Arcade;
import org.hibernate.Session;

import java.util.List;

public class ArcadeDaoHibernate extends GenericDaoHibernate<Arcade, Long> implements ArcadeDao {
    public ArcadeDaoHibernate() {
        super(Arcade.class);
    }

    @Override
    public List<Arcade> findByName(Session session, String name) {
        return session.createNamedQuery("Arcade.findByNamePattern", Arcade.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
