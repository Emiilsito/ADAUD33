package com.example.dao.hibernateimpl;

import com.example.dao.UserDao;
import com.example.domain.User;

public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao {

    public UserDaoHibernate() {
        super(User.class);
    }
}
