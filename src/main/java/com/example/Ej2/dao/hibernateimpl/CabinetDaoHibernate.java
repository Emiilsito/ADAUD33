package com.example.Ej2.dao.hibernateimpl;

import com.example.Ej1.dao.hibernateimpl.GenericDaoHibernate;
import com.example.Ej2.dao.CabinetDao;
import com.example.Ej2.domain.Cabinet;
import org.hibernate.Session;

import java.util.List;

public class CabinetDaoHibernate extends GenericDaoHibernate<Cabinet, Long> implements CabinetDao {
    public CabinetDaoHibernate() {
        super(Cabinet.class);
    }

    @Override
    public List<Cabinet> findActiveByGenre(Session session, String genero) {
        String query = """
                SELECT c
                FROM Cabinet c
                JOIN FETCH c.game g
                WHERE c.status = 'ACTIVE'
                AND g.genre = :genre
                """;
        return session.createQuery(query, Cabinet.class)
                .setParameter("genre", genero)
                .getResultList();
    }
}
