package com.fast.monitorserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.monitorserver.dao.IRsPcUserDao;
import com.fast.monitorserver.entity.RsPcUser;
import com.fast.monitorserver.service.IRsPcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Nelson on 2020/1/10.
 */
@Service
public class RsPcUserImpl extends AbstractService<RsPcUser> implements IRsPcUser {
    @Autowired
    private IRsPcUserDao rsPcUserDao;
    @Override
    public JpaRepository<RsPcUser, String> getRepository() {
        return rsPcUserDao;
    }

    @Override
    public RsPcUser findUser() {
      return rsPcUserDao.findById(1);
    }
}
