package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPATaskDao;
import com.blueprismserver.entity.BPATask;
import com.blueprismserver.entity.BPAUser;
import com.blueprismserver.service.IBPATask;
import com.blueprismserver.service.IBPAUser;
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
