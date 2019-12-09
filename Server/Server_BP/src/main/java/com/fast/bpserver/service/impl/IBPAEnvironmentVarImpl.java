package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPAEnvironmentVarDao;
import com.fast.bpserver.entity.BPAEnvironmentVar;
import com.fast.bpserver.service.IBPAEnviornmentVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nelson on 2019/11/11.
 */
@Service
public class IBPAEnvironmentVarImpl extends AbstractService<BPAEnvironmentVar> implements IBPAEnviornmentVar {
    @Autowired
    private IBPAEnvironmentVarDao ibpaEnvironmentVarDao;
    @Override
    public JpaRepository<BPAEnvironmentVar,String> getRepository() {
        return ibpaEnvironmentVarDao;
    }

    public List<BPAEnvironmentVar> findAll(){
        return ibpaEnvironmentVarDao.findAll();
    }
}
