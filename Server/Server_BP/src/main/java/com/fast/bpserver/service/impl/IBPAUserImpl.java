package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPAUserDao;
import com.fast.bpserver.entity.BPAUser;
import com.fast.bpserver.service.IBPAUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by sjlor on 2019/10/29.
 */
@Service
public class IBPAUserImpl extends AbstractService<BPAUser> implements IBPAUser{
    @Autowired
    private IBPAUserDao ibpaUserDao;
    @Autowired
    public JpaRepository<BPAUser,String> getRepository(){return ibpaUserDao;}
}
