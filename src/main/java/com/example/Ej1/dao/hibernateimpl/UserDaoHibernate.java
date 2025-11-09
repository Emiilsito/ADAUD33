package com.example.Ej1.dao.hibernateimpl;

import com.example.Ej1.dao.UserDao;
import com.example.Ej1.domain.User;

public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao {

    public UserDaoHibernate() {
        super(User.class);
    }
}
