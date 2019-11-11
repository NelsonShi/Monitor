package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPAScheduleDao;
import com.blueprismserver.entity.BPASchedule;
import com.blueprismserver.service.IBPASchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by sjlor on 2019/11/11.
 */
@Service
public class IBPAScheduleImpl extends AbstractService<BPASchedule> implements IBPASchedule {
    @Autowired
    private IBPAScheduleDao scheduleDao;
    @Override
    public JpaRepository<BPASchedule,String> getRepository(){return scheduleDao;}
}
