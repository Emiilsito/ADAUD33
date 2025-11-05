package com.example.service;

import com.example.config.HibernateUtil;
import com.example.dao.UserDao;
import com.example.dao.hibernateimpl.UserDaoHibernate;
import org.hibernate.SessionFactory;

public class UserService {
    private final SessionFactory sf;
    private final UserDao userDao;

    public UserService() {
        this.sf = HibernateUtil.getSessionFactory();
        this.userDao = new UserDaoHibernate();
    }

    public
}
