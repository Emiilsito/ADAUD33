package com.example.Ej1.dao;

import com.example.Ej1.domain.Space;
import com.example.Ej1.dto.MostProfitSpacesDto;
import org.hibernate.Session;

import java.util.List;

public interface SpaceDao extends GenericDao<Space, Long>{

    List<MostProfitSpacesDto> findTop3MostProfitSpaces(Session session);

    List<Space> getNeverReservedSpaces(Session session);
}
