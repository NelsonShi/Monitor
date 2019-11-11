package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPAEnvironmentVarDao;
import com.blueprismserver.entity.BPAEnvironmentVar;
import com.blueprismserver.service.IBPAEnviornmentVar;
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
