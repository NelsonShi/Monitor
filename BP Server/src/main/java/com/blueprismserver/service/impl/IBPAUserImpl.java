package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPAUserDao;
import com.blueprismserver.entity.BPAUser;
import com.blueprismserver.service.IBPAUser;
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
