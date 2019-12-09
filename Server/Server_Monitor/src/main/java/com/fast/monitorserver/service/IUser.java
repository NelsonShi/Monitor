package com.fast.monitorserver.service;

import com.fast.bpserver.base.IService;
import com.fast.monitorserver.entity.User;


/**
 * Created by Nelson on 2019/12/6.
 */
public interface IUser extends IService<User> {
    User findLoginUser(User user);
}
