package com.example.Ej1.dao;

import com.example.Ej1.domain.Tag;
import com.example.Ej1.dto.CityTagCountDto;
import com.example.Ej1.dto.MostUsedTagDto;
import org.hibernate.Session;

import java.util.List;

public interface TagDao extends GenericDao<Tag, Long>{

    List<MostUsedTagDto> findMostUsedTags(Session session);

    List<Tag> findTagsByPrefix(Session session, String prefix);

    List<CityTagCountDto> countSpacesByCityAndTag(Session session);
}
