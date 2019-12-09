package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPATaskSessionDao;
import com.fast.bpserver.entity.BPATaskSession;
import com.fast.bpserver.service.IBPATaskSession;
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
