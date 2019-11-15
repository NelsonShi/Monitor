package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPAScheduleTriggerDao;
import com.blueprismserver.entity.BPASchedule;
import com.blueprismserver.entity.BPAScheduleTrigger;
import com.blueprismserver.entity.BPASession;
import com.blueprismserver.service.IBPASchedule;
import com.blueprismserver.service.IBPAScheduleTrigger;
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
