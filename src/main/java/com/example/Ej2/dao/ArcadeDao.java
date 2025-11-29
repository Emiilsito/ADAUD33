package com.example.Ej2.dao;

import com.example.Ej1.dao.GenericDao;
import com.example.Ej2.domain.Arcade;
import com.example.Ej2.dto.ArcadeEstimatedIncome;
import org.hibernate.Session;

import java.util.List;

public interface ArcadeDao extends GenericDao<Arcade, Long> {

    List<Arcade> findByName(Session session, String name);

    List<ArcadeEstimatedIncome> estimatedIncomeByArcade(Session session);
}
