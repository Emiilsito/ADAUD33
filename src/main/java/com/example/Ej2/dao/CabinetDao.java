package com.example.Ej2.dao;

import com.example.Ej1.dao.GenericDao;
import com.example.Ej2.domain.Cabinet;
import org.hibernate.Session;

import java.util.List;

public interface CabinetDao extends GenericDao<Cabinet, Long> {

    List<Cabinet> findActiveByGenre(Session session, String genero);
}
