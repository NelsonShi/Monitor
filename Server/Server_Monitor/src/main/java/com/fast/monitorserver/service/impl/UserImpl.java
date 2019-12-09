package com.fast.monitorserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.monitorserver.dao.IUserDao;
import com.fast.monitorserver.entity.User;
import com.fast.monitorserver.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Nelson on 2019/12/6.
 */
@Service
public class UserImpl extends AbstractService<User> implements IUser {
    @Autowired
    private IUserDao userDao;

    @Override
    public JpaRepository<User, String> getRepository() {
        return userDao;
    }

    @Override
    public User findLoginUser(User user) {
        return userDao.findByLoginNameAndPassword(user.getLoginName(),user.getPassword());
    }
}
