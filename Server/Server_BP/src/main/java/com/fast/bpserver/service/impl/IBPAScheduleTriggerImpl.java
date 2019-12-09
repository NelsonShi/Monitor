package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPAScheduleTriggerDao;
import com.fast.bpserver.entity.BPAScheduleTrigger;
import com.fast.bpserver.service.IBPAScheduleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sjlor on 2019/11/11.
 */
@Service
public class IBPAScheduleTriggerImpl extends AbstractService<BPAScheduleTrigger> implements IBPAScheduleTrigger {
    @Autowired
    private IBPAScheduleTriggerDao ibpaScheduleTriggerDao;
    @Override
    public JpaRepository<BPAScheduleTrigger,String> getRepository(){return ibpaScheduleTriggerDao;}

    public List<BPAScheduleTrigger> findAll(){
        return  ibpaScheduleTriggerDao.findAll();
    }
}
