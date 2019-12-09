package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPATaskDao;
import com.fast.bpserver.entity.BPATask;
import com.fast.bpserver.service.IBPATask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sjlor on 2019/11/11.
 */
@Service
public class IBPATaskImpl extends AbstractService<BPATask> implements IBPATask {
    @Autowired
    private IBPATaskDao taskDao;
    @Override
    public JpaRepository<BPATask,String> getRepository(){return  taskDao;}

    public List<BPATask> findAll(){return taskDao.findAll();}
}
