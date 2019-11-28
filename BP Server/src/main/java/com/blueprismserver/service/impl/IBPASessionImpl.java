package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPASessionDao;
import com.blueprismserver.entity.BPAResource;
import com.blueprismserver.entity.BPASession;
import com.blueprismserver.entity.BPAUser;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.entity.QueryVo.BPASessionLogs;
import com.blueprismserver.service.IBPASession;
import com.blueprismserver.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sjlor on 2019/10/29.
 */
@Service
public class IBPASessionImpl extends AbstractService<BPASession> implements IBPASession {
    @Autowired
    private IBPASessionDao bpaSessionDao;

    @Override
    public JpaRepository<BPASession,String> getRepository(){return bpaSessionDao;}

    @Override
    public void RunProcess(BPAUser bpaUser, BPAResource bpaResource, BPAProcess bpaProcess){
         BPASession bpaSession=new BPASession();
         bpaSession.setSessionid(UUID.randomUUID().toString());
         bpaSession.setStartdatetime(new Date());
         bpaSession.setProcessid(bpaProcess.getProcessid());
         bpaSession.setStarterresourceid(bpaResource.getResourceid());
         bpaSession.setStarteruserid(bpaUser.getUserid());
         bpaSession.setRunningresourceid(bpaResource.getResourceid());
         bpaSession.setStatusid(0);
         bpaSession.setStartparamsxml("<inputs/>");
         bpaSession.setStarttimezoneoffset(0);
         bpaSessionDao.save(bpaSession);
    }


    public List<BPASessionLogs> findErrorSessionAndLogs(String date){
        List<Object[]> list=bpaSessionDao.findErrorSessionAndLogs(date);
        List<BPASessionLogs> sessionLogsList= EntityUtils.castEntity(list,BPASessionLogs.class,new BPASessionLogs());
        return sessionLogsList;
    }

    @Override
    public List<BPASession> recentlySession(){
        return bpaSessionDao.findRecentSession();
    }
}
