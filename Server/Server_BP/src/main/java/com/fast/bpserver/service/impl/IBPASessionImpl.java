package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPASessionDao;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.postEntity.SessionParams;
import com.fast.bpserver.entity.postEntity.WebSocketCommand;
import com.fast.bpserver.entity.vo.PCMessageResult;
import com.fast.bpserver.entity.vo.SessionControlResult;
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
import java.util.Map;
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
    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public JpaRepository<BPASession,String> getRepository(){return bpaSessionDao;}

    @Override
    public SessionControlResult PendingProcess(BPAUser bpaUser,BPAProcess bpaProcess,Integer timeSpan,Channel channel,String ip){
        SessionControlResult result=new SessionControlResult();
        BPASession existSession=isExisted(bpaProcess);
        if(existSession==null){
            BPAInternalAuth bpaInternalAuth=ibpaInternalAuthService.GenrateInternalAuth(bpaUser.getUserId(),bpaProcess.getProcessid());
            result=PendingSession(bpaInternalAuth,"createas",channel,ip);
        }else {
            log.info("Process pending on Resource, ID={}",existSession.getRunningresourceid());
            result.setResult(3);
            result.setMessage("Process pending on Resource ,Session not exist");
        }
        return  result;
    }

    @Override
    public SessionControlResult DeleteSession(BPAUser bpaUser, String sessionId, Channel channel,String ip) {
        BPASession existSession=bpaSessionDao.findBySessionid(sessionId);
        SessionControlResult result=new SessionControlResult();
        if(existSession==null){
            log.info("DeleteSession seesion does not exist, sessionId={}",sessionId);
            result.setResult(3);
            result.setMessage("Delete Session on Resource ,Session not exist");
            return result;
        }
        BPAInternalAuth bpaInternalAuth=ibpaInternalAuthService.GenrateInternalAuth(bpaUser.getUserId(),existSession.getProcessid());
        result=  DeleteSession(bpaInternalAuth,channel,sessionId,ip);
        return result;
    }

    @Override
    public SessionControlResult StopSession(BPAUser bpaUser, String sessionId, Channel channel,String resourceName,String ip) {
        BPASession existSession=bpaSessionDao.findBySessionid(sessionId);
        SessionControlResult result=new SessionControlResult();
        if(existSession==null){
            log.info("StopSession seesion does not exist, sessionId={}",sessionId);
            result.setResult(3);
            result.setMessage("Stop Session on Resource ,Session not exist");
            return result;
        }
        if(existSession.getStatusid()==1){
           result= StopSession(channel,sessionId,bpaUser.getUserId(),resourceName,ip);
        }else {
            result.setResult(3);
            result.setMessage("Stop Session on Resource ,Session not running");
            log.info("StopSession seesion does not running, sessionId={}",sessionId);
        }
        return result;
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
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
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

    public SessionControlResult StartSession(Channel channel,String ip,String resourceId){
        if(channel==null)return null;
        String lastStr=cacheUtil.getPCMessageMap().get(ip);
        channel.writeAndFlush("startp\r");
        SessionControlResult result=getPCResponse(lastStr,ip);
        if(result.getResult()!=1||!(result.getMessage().toUpperCase().contains("PARAMETERS SET"))){
            return result;
        }
        BPASession existSession=findPendingSession(resourceId);
        if(existSession==null){
            result.setResult(3);
            result.setMessage("Session didn't exist");
            return result;
        }
        BPAInternalAuth bpaInternalAuth=ibpaInternalAuthService.GenrateInternalAuth(existSession.getStarteruserid(),existSession.getProcessid());
        lastStr=cacheUtil.getPCMessageMap().get(ip);
        String message="startas "+bpaInternalAuth.getUserId()+"_"+bpaInternalAuth.getToken()+" "+existSession.getSessionid()+"\r";
        channel.writeAndFlush(message);
        result=getPCResponse(lastStr,ip);
        return  result;
    }

    private SessionControlResult PendingSession(BPAInternalAuth bpaInternalAuth, String command, Channel channel, String ip){
        if(channel==null)return null;
        String lastStr=cacheUtil.getPCMessageMap().get(ip);
        String message=command+" "+bpaInternalAuth.getUserId()+"_"+bpaInternalAuth.getToken()+" "+bpaInternalAuth.getProcessId()+"\r";
        channel.writeAndFlush(message);
        SessionControlResult result=getPCResponse(lastStr,ip);
        return result;
    }

    private SessionControlResult DeleteSession(BPAInternalAuth bpaInternalAuth,Channel channel,String sessionId,String ip){
        if(channel==null)return null;
        String lastStr=cacheUtil.getPCMessageMap().get(ip);
        String message="deleteas"+" "+bpaInternalAuth.getUserId()+"_"+bpaInternalAuth.getToken()+" "+sessionId+"\r";
        channel.writeAndFlush(message);
        SessionControlResult result=getPCResponse(lastStr,ip);
        return  result;
    }

    private SessionControlResult StopSession(Channel channel,String sessionId,String userId,String resourceName,String ip){
        if(channel==null)return null;
        String lastStr=cacheUtil.getPCMessageMap().get(ip);
        String message="stop"+" "+sessionId+" "+userId+" "+"name"+" "+resourceName+"\r";
        channel.writeAndFlush(message);
        SessionControlResult result=getPCResponse(lastStr,ip);
        return  result;
    }

    private SessionControlResult getPCResponse(String lastStr, String ipAddress){
        SessionControlResult result=new SessionControlResult();
        try {
            Map<String,String> messageMap=cacheUtil.getPCMessageMap();
            long lastTime=lastStr==null?0:new Long(lastStr.split("_")[0]);
            for(int i=0;i<=50;i++){
                if(i==50){
                    result.setResult(0);
                    result.setMessage("get PC Response time out");
                    break;
                }
                Thread.sleep(100);
                String message=messageMap.get(ipAddress);
                if(message==null)continue;
                long time=new Long(message.split("_")[0]);
                if(time>lastTime){
                   result.setResult(1);
                   result.setMessage(message.split("_")[1]);
                   break;
                }else {
                    continue;
                }
            }
        }catch (Exception ex){
            result.setResult(2);
            result.setMessage("Error::get pc response method exception");
        }
        return result;
    }
}
