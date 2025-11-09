package com.example.Ej1.service;

import com.example.Ej1.config.HibernateUtil;
import com.example.Ej1.dao.AccessCardDao;
import com.example.Ej1.dao.UserDao;
import com.example.Ej1.dao.hibernateimpl.AccessCardDaoHibernate;
import com.example.Ej1.dao.hibernateimpl.UserDaoHibernate;
import com.example.Ej1.domain.AccessCard;
import com.example.Ej1.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private final SessionFactory sf;
    private final UserDao userDao;
    private final AccessCardDao accessCardDao;

    public UserService() {
        this.sf = HibernateUtil.getSessionFactory();
        this.userDao = new UserDaoHibernate();
        this.accessCardDao = new AccessCardDaoHibernate();
    }

    public Long create(User u){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            if (u.getCreatedAt() == null){
                u.setCreatedAt(LocalDateTime.now());
            }
            userDao.create(s, u);
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

    public List<User> findAll() {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<User> users = userDao.findAll(s);
            tx.commit();
            return users;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
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

    public void assignAccessCard(Long userId, String uid){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            User user = userDao.findById(s, userId);
            if (user == null){
                throw new IllegalArgumentException("Usuario con Id " + userId + " no existe.");
            }

            AccessCard card = new AccessCard();
            card.setCardUid(uid);
            card.setActive(true);
            card.setUser(user);
            card.setIssuedAt(LocalDateTime.now());

            accessCardDao.create(s, card);
            user.setAccessCard(card);
            userDao.update(s, user);

            tx.commit();
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void updateAccessCard(Long userId, boolean active, String newUid){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            User user = userDao.findById(s, userId);
            if (user == null || user.getAccessCard() == null){
                throw new IllegalArgumentException("El usuario no tiene una tarjeta asignada.");
            }

            AccessCard card = user.getAccessCard();
            card.setCardUid(newUid);
            accessCardDao.update(s, card);

            tx.commit();
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void removeAccessCard(Long userId){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            User user = userDao.findById(s, userId);
            if (user == null || user.getAccessCard() == null){
                throw new IllegalArgumentException("El usuario no tiene una tarjeta asignada.");
            }

            AccessCard cardEliminate = user.getAccessCard();
            user.setAccessCard(null);
            userDao.update(s, user);
            accessCardDao.delete(s, cardEliminate);

            tx.commit();
        } catch (RuntimeException e){
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
