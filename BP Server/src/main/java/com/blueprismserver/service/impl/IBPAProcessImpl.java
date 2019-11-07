package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IProcessDao;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.service.IBPAProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sjlor on 2019/10/24.
 */
@Service
public class IBPAProcessImpl extends AbstractService<BPAProcess> implements IBPAProcess {
   @Autowired
   private IProcessDao processDao;
    @Override
    public JpaRepository<BPAProcess,String> getRepository() {
        return processDao;
    }

    @Override
    public List<BPAProcess> findByProcessType(String type){
        return processDao.findByProcessType(type);
    }

    public  List<BPAProcess> findAll(){
        return processDao.findAll();
    }
}
