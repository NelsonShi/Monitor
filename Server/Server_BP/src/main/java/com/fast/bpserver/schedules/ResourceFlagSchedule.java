package com.fast.bpserver.schedules;

import com.fast.bpserver.dao.IBPAResourceDao;
import com.fast.bpserver.dao.IBPASessionDao;
import com.fast.bpserver.service.IBPAResource;
import com.fast.bpserver.service.IBPASession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nelson on 2019/12/27.
 */
@Component
public class ResourceFlagSchedule {
    private static Logger log= LoggerFactory.getLogger(ResourceFlagSchedule.class);
    @Autowired
    private IBPAResource ibpaResourceService;

    @Scheduled(initialDelay=1000,fixedDelay=5000)
    private void run(){
         Date dateBegin=new Date();
         Boolean updated=ibpaResourceService.UpdateCacheResourceList();
         if(updated) log.info("schedule 耗时：：{}",new Date().getTime()-dateBegin.getTime());
    }
}
