package com.example.Ej2.dao;

import org.hibernate.Session;

import java.util.List;

public interface GenericDao<T, ID>  {

    ID create(Session s, T entity);

    T findById(Session s, ID id);

    T update(Session s, T entity);

    void delete(Session s, T entity);

    boolean deleteById(Session s, ID id);

    List<T> findAll(Session s);

}