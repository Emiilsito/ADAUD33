package com.example.dao.hibernateimpl;

import com.example.dao.GenericDao;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Optional;

public class GenericDaoHibernate<T, ID extends Serializable> implements GenericDao<T, ID> {
    private final Class<T> entityClass;

    public GenericDaoHibernate(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    @Override
    public ID save(Session s, T entity) {
        s.persist(entity);
        s.flush();
        return (ID) s.getIdentifier(entity);
    }

    @Override
    public T findById(Session s, ID id) {
        return s.find(entityClass, id);
    }

    @Override
    public T update(Session s, T entity) {
        return s.merge(entity);
    }

    @Override
    public void delete(Session s, T entity) {
        s.remove(entity);
    }

    @Override
    public boolean deleteById(Session s, ID id) {
        T found = s.find(entityClass, id);
        if (found != null){
            s.remove(found);
            return true;
        }
        return false;
    }
}
