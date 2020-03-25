package com.fast.monitorserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.entity.BPAUser;
import com.fast.monitorserver.dao.IRsPcUserDao;
import com.fast.monitorserver.dao.IUserDao;
import com.fast.monitorserver.entity.RsPcUser;
import com.fast.monitorserver.entity.User;
import com.fast.monitorserver.service.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2019/12/6.
 */
@Service
public class UserImpl extends AbstractService<User> implements IUser {
    private static Logger log= LoggerFactory.getLogger(UserImpl.class);
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRsPcUserDao pcUserDao;

    @Override
    public JpaRepository<User, String> getRepository() {
        return userDao;
    }

    @Override
    public User findLoginUser(User user) {
        return userDao.findByLoginNameAndPassword(user.getLoginName(),user.getPassword());
    }

    @Override
    public void SyncFromBp(List<BPAUser> bpaUserList) {
        List<User> userList=userDao.findAll();
        Map<String,User> userMap=new HashMap<>(userList.size());
        RsPcUser pcUser=pcUserDao.findAll().get(0);
        for (User user:userList){
            userMap.put(user.getBpUserId(),user);
        }
        String userId="";
        List<User> saveList=new ArrayList<>();
        for (BPAUser bpaUser:bpaUserList){
            if(userMap.get(bpaUser.getUserId())==null) {
                saveList.add(createUser(bpaUser));
            }
            if(bpaUser.getUserName()!=null&&bpaUser.getUserName().equals(pcUser.getLoginName())){
                userId=bpaUser.getUserId();
            }
        }
        if(saveList.size()>0){
            log.info("Use sync form BP ::"+saveList.size());
            userDao.saveAll(saveList);
        }
        if(StringUtils.isEmpty(pcUser.getUserId())){
            pcUser.setUserId(userId);
            pcUserDao.save(pcUser);
        }

    }

    private User createUser(BPAUser bpaUser){
        User user=new User();
        user.setName(bpaUser.getUserName());
        user.setGender(1);
        user.setLoginName(bpaUser.getUserName());
        user.setPassword("1");
        user.setDepartmentId(1);
        user.setBpUserId(bpaUser.getUserId());
        user.setStatus(0);
        return user;
    }
}
