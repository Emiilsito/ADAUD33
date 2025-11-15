package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.TagDao;
import com.example.Ej1.domain.Tag;
import com.example.Ej1.dto.CityTagCountDto;
import com.example.Ej1.dto.MostUsedTagDto;
import org.hibernate.Session;

import java.util.List;

public class TagDaoHibernate extends GenericDaoHibernate<Tag, Long> implements TagDao{

    public TagDaoHibernate() {
        super(Tag.class);
    }

    @Override
    public List<MostUsedTagDto> findMostUsedTags(Session session) {
        String query = """
                SELECT new com.example.Ej1.dto.MostUsedTagDto(
                    t.id, t.name, COUNT(sp)
                )
                FROM Space sp
                JOIN sp.tags t
                GROUP BY t.id, t.name
                ORDER BY COUNT(sp) DESC
                """;

        return session.createQuery(query, MostUsedTagDto.class)
                .getResultList();
    }

    @Override
    public List<Tag> findTagsByPrefix(Session session, String prefix) {
        String query = "FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT(:prefix, '%'))";

        return session.createQuery(query, Tag.class)
                .setParameter("prefix", prefix)
                .getResultList();
    }

    @Override
    public List<CityTagCountDto> countSpacesByCityAndTag(Session session) {
        String query = """
        SELECT new com.example.Ej1.dto.CityTagCountDto(
            v.city, t.name, COUNT(sp)
        )
        FROM Space sp
        JOIN sp.venue v
        JOIN sp.tags t
        GROUP BY v.city, t.name
        ORDER BY v.city, t.name
        """;

        return session.createQuery(query, CityTagCountDto.class)
                .getResultList();
    }
}
