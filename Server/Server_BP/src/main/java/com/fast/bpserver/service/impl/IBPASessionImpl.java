package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPASessionDao;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.entity.BPAResource;
import com.fast.bpserver.entity.BPASession;
import com.fast.bpserver.entity.BPAUser;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.postEntity.WebSocketCommand;
import com.fast.bpserver.nettyServer.GlobalUserUtil;
import com.fast.bpserver.schedules.ResourceFlagSchedule;
import com.fast.bpserver.service.IBPASession;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.EntityUtils;
import com.fast.bpserver.utils.JsonToObjectUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by sjlor on 2019/10/29.
 */
@Service
public class IBPASessionImpl extends AbstractService<BPASession> implements IBPASession {
    private static Logger log= LoggerFactory.getLogger(IBPASessionImpl.class);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Autowired
    private IBPASessionDao bpaSessionDao;
    @Autowired
    private CacheUtil cacheUtil;

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
