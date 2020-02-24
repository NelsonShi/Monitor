package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPASessionDao;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.postEntity.SessionParams;
import com.fast.bpserver.entity.postEntity.WebSocketCommand;
import com.fast.bpserver.nettyServer.GlobalUserUtil;
import com.fast.bpserver.schedules.ResourceFlagSchedule;
import com.fast.bpserver.service.IBPAInternalAuth;
import com.fast.bpserver.service.IBPASession;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.EntityUtils;
import com.fast.bpserver.utils.JsonToObjectUtil;
import com.fast.bpserver.utils.TimeZoneUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private IBPAInternalAuth ibpaInternalAuthService;


    @Override
    public JpaRepository<BPASession,String> getRepository(){return bpaSessionDao;}

    @Override
    public void PendingProcess(BPAUser bpaUser,BPAProcess bpaProcess,Integer timeSpan,Channel channel){
        BPASession existSession=isExisted(bpaProcess);
        if(existSession==null){
            BPAInternalAuth bpaInternalAuth=ibpaInternalAuthService.GenrateInternalAuth(bpaUser.getUserId(),bpaProcess.getProcessid());
            NoticePCListener(bpaInternalAuth,"createas",channel);
        }else {
            log.info("Process pending on Resource, ID={}",existSession.getRunningresourceid());
        }
    }

    private BPASession isExisted(BPAProcess process){
        return bpaSessionDao.findByProcessidAndStatusid(process.getProcessid(),0);
    }

    public BPASession findPendingSession(String resourceId){
       return bpaSessionDao.findByRunningresourceidAndStatusid(resourceId,0);
    }

    private boolean SaveProcessSession(BPAUser bpaUser, BPAResource bpaResource, BPAProcess bpaProcess,Integer timeSpan){
        BPASession bpaSession=new BPASession();
        bpaSession.setSessionid(UUID.randomUUID().toString());
        bpaSession.setStartdatetime(TimeZoneUtil.formateDateToZone(new Date(),timeSpan));
        bpaSession.setProcessid(bpaProcess.getProcessid());
        bpaSession.setStarterresourceid(bpaResource.getResourceid());
        bpaSession.setStarteruserid(bpaUser.getUserId());
        bpaSession.setRunningresourceid(bpaResource.getResourceid());
        bpaSession.setStatusid(0);
//        bpaSession.setStartparamsxml("<inputs/>");
        bpaSession.setStarttimezoneoffset(0);
        bpaSession.setEndtimezoneoffset(0);
        bpaSession.setLastupdatedtimezoneoffset(0);
//        bpaSession.setWarningthreshold(300);
        BPASession session= bpaSessionDao.save(bpaSession);
        if(session!=null){
            log.info("Pending process success, Session id={},processName={}",session.getSessionid(),bpaProcess.getName());
            return true;
        }else {
            return false;
        }
    }


    public List<BPASessionLogs> findErrorSessionAndLogs(String date){
        List<Object[]> list=bpaSessionDao.findErrorSessionAndLogs(date);
        List<BPASessionLogs> sessionLogsList= EntityUtils.castEntity(list,BPASessionLogs.class,new BPASessionLogs());
        return sessionLogsList;
    }

    @Override
    public BPASession findPendingProcess(String processId) {
        return bpaSessionDao.findByProcessidAndStatusid(processId,0);
    }



    @Override
    public List<BPASession> findAllPendingSessions() {
        return bpaSessionDao.findByStatusid(0);
    }

    public List<BPASession> findSessionWithParams(SessionParams params){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/DD");
        Date startTime=GenerateTimeZoneDate(params.getStartTime(),params.getTimeZone(),sdf);
        Date endTime=GenerateTimeZoneDate(params.getEndTime(),params.getTimeZone(),sdf);
        List<BPASession> sessionList=bpaSessionDao.findAll(new Specification<BPASession>() {
           @Nullable
           @Override
           public Predicate toPredicate(Root<BPASession> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
               Predicate predicate=criteriaBuilder.conjunction();
               if(!StringUtils.isEmpty(params.getResourceId())){
                   predicate.getExpressions().add(criteriaBuilder.equal(root.get("runningresourceid"),params.getResourceId()));
               }
               if(params.getStatus()!=null&&params.getStatus()>=0){
                   predicate.getExpressions().add(criteriaBuilder.equal(root.get("statusid"),params.getStatus()));
               }
               if(startTime!=null){
                   predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("startdatetime"),startTime));
               }
               if(endTime!=null){
                   predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("enddatetime"),endTime));
               }
               return predicate;
           }
       });
       return sessionList;
    }

    private Date GenerateTimeZoneDate(String date,Integer timeZone,SimpleDateFormat sdf){
        if(StringUtils.isEmpty(date)){
            return null;
        }
        try {
            Date time=sdf.parse(date);
            Date resDate=new Date(time.getTime()+60*60*1000*timeZone);
            return resDate;
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<BPASession> recentlySession(){
        return bpaSessionDao.findRecentSession();
    }


    private void NoticePCListener(BPAInternalAuth bpaInternalAuth,String command,Channel channel){
        if(channel==null)return;
        String message=command+" "+bpaInternalAuth.getUserId()+"_"+bpaInternalAuth.getToken()+" "+bpaInternalAuth.getProcessId()+"\r";
        channel.writeAndFlush(message);
    }

}
