package com.fast.monitorserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAUser;
import com.fast.monitorserver.entity.User;

import java.util.List;


/**
 * Created by Nelson on 2019/12/6.
 */
public interface IUser extends IService<User> {
    User findLoginUser(User user);
    void SyncFromBp(List<BPAUser> userList);
}
