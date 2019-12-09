package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPASessionDao;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.entity.BPAResource;
import com.fast.bpserver.entity.BPASession;
import com.fast.bpserver.entity.BPAUser;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.service.IBPASession;
import com.fast.bpserver.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
