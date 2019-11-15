package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPATaskSessionDao;
import com.blueprismserver.entity.BPATaskSession;
import com.blueprismserver.service.IBPATaskSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sjlor on 2019/11/11.
 */
@Service
public class IBPATaskSessionImpl extends AbstractService<BPATaskSession> implements IBPATaskSession {
    @Autowired
    private IBPATaskSessionDao taskSessionDao;
    @Override
    public JpaRepository<BPATaskSession,String> getRepository(){return taskSessionDao;}

    public List<BPATaskSession> findAll(){
        return taskSessionDao.findAll();
    }
}
