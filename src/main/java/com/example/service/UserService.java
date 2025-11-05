package com.example.service;

import com.example.config.HibernateUtil;
import com.example.dao.AccessCardDao;
import com.example.dao.UserDao;
import com.example.dao.hibernateimpl.UserDaoHibernate;
import com.example.domain.AccessCard;
import com.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class UserService {
    private final SessionFactory sf;
    private final UserDao userDao;

    public UserService() {
        this.sf = HibernateUtil.getSessionFactory();
        this.userDao = new UserDaoHibernate();
    }

    public Long save(User u){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            userDao.save(s, u);
            tx.commit();
            return u.getId();
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public User findById(Long id){
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            User userFinded = userDao.findById(s, id);
            tx.commit();
            return userFinded;
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
            throw e;
        }
    }

    public void update(User u){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            userDao.update(s, u);
            tx.commit();
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
            throw e;
        }
    }

    public void delete(User u){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            userDao.delete(s, u);
            tx.commit();
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
            throw e;
        }
    }

    public void deleteById(Long id){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            userDao.deleteById(s, id);
            tx.commit();
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
            throw e;
        }
    }

    public void assignAccessCard(Long userId, Long uid){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            User user = userDao.findById(s, userId);
            AccessCard accessCard = AccessCardDao.




        }
    }
}
