package com.fast.monitorserver.schedules;

import com.fast.bpserver.entity.BPAResource;
import com.fast.bpserver.entity.BPAUser;
import com.fast.bpserver.service.IBPAResource;
import com.fast.bpserver.service.IBPAUser;
import com.fast.monitorserver.service.IResourceIpRef;
import com.fast.monitorserver.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Nelson on 2020/3/24.
 *
 * 从BP 数据库同步数据至此程序数据库
 */
@Component
public class SyncFromBpToLocal {
    @Autowired
    private IBPAResource resourceService;
    @Autowired
    private IBPAUser bpaUserService;
    @Autowired
    private IResourceIpRef resourceIpRefService;
    @Autowired
    private IUser userService;
    @Scheduled(initialDelay=1000,fixedDelay=10*60*1000)
    private void run(){
        //Sync Resource
        Object[] lastUpdateResource=resourceService.findMaxUpdatedItem();
        Object[] lastRef=resourceIpRefService.findLastUpdate();
        long resourceLastUpdateTime=lastUpdateResource[0]==null?0:((Date)lastUpdateResource[0]).getTime();
        long refLastUpdateTime=lastRef[0]==null?0:((Date)lastRef[0]).getTime();
        if(refLastUpdateTime<resourceLastUpdateTime){
            List<BPAResource> resourceList=resourceService.findAll();
            resourceIpRefService.SyncFromBp(resourceList);
        }

        //Sync User
        List<BPAUser> bpaUserList=bpaUserService.findAll();
        userService.SyncFromBp(bpaUserList);

    }
}
