package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPAResourceDao;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.postEntity.WebSocketCommand;
import com.fast.bpserver.entity.vo.BPAResourceVo;
import com.fast.bpserver.entity.vo.ComputerData;
import com.fast.bpserver.nettyServer.GlobalUserUtil;
import com.fast.bpserver.service.IBPAResource;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.EntityUtils;
import com.fast.bpserver.utils.JsonToObjectUtil;
import com.fast.bpserver.utils.TimeZoneUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sjlor on 2019/10/24.
 */
@Service
public class IBPAResourceImpl extends AbstractService<BPAResource> implements IBPAResource {
    private static Logger logger = LoggerFactory.getLogger(IBPAResourceImpl.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Autowired
    private IBPAResourceDao bpaResourceDao;
    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public JpaRepository<BPAResource, String> getRepository() {
        return bpaResourceDao;
    }

    public List<BPAResource> findAll() {
        return bpaResourceDao.findAll();
    }

    public Object[] findMaxUpdatedItem(){
        Object[] resource=bpaResourceDao.findMaxLastUpdatedItem();
        return resource;
    }

    @Override
    public List<BPAResourceVo> GenrateListWithResourceAndUser(Map<String, ComputerData> computerDataMap, Map<String, BPAUser> userMap, List<BPAResource> resourceList,
                                                              Map<String, BPASession> sessionMap, Map<String, BPAProcess> processMap, Map<String, BPAEnvironmentVar> envMap, Integer timeSpan) {
        List<BPAResourceVo> bpaResourceVoList = new ArrayList<>();
        for (BPAResource resource : resourceList) {
            BPAResourceVo vo = new BPAResourceVo();
            vo.setResourceid(resource.getResourceid());
            vo.setName(resource.getName());
            BPASession session = sessionMap.get(resource.getResourceid());
            if (session != null) {
                BPAProcess p = processMap.get(session.getProcessid());
                if (p != null) {
                    vo.setProcessStatus(session.getStatusid());
                    vo.setProcessName(p.getName());
                    if (session.getStatusid() == 0 || session.getStatusid() == 1) {
                        Date startTime = TimeZoneUtil.formateDateToZone(session.getStartdatetime(), timeSpan);
                        String timeSlot = sdf.format(startTime);
                        BPAEnvironmentVar enVar = envMap.get("RT - " + p.getName());
                        if (enVar == null) {
                            timeSlot += "--- Undefined";
                        } else {
                            timeSlot += ("---" + DateAddTimeWithString(startTime, enVar.getValue()));
                        }
                        vo.setTimeSlot(timeSlot);
                    } else {
                        vo.setTimeSlot("");
                    }
                } else {
                    vo.setProcessStatus(3);
                    vo.setProcessName("error:can not find process");
                    vo.setTimeSlot("");
                }
            } else {
                vo.setProcessStatus(-1);
                vo.setProcessName("");
                vo.setTimeSlot("");
            }
            vo.setLastupdated(sdf.format(TimeZoneUtil.formateDateToZone(resource.getLastupdated(), timeSpan)));
            vo.setAttributeID(resource.getAttributeID());
            vo.setFQDN(resource.getFQDN());
            vo.setUserName(resource.getUserID() == null ? "" : userMap.get(resource.getUserID()) == null ? "" : userMap.get(resource.getUserID()).getUserName());
            vo.setStatusid(resource.getStatusid());
            vo.setDisplayStatus(resource.getDisplayStatus());
            if (computerDataMap == null || computerDataMap.isEmpty()) {
                vo.setBotIp("Netty error:404");
                vo.setIsNettyConnected(0);
            } else {
                boolean hasNettyConnected = computerDataMap.get(vo.getName()) != null;
                vo.setBotIp(hasNettyConnected ? computerDataMap.get(vo.getName()).getBotIP() : "");
                vo.setIsNettyConnected(hasNettyConnected ? 1 : 0);
            }
            vo.setAvailable(vo.getProcessStatus()>=2&&vo.getProcessStatus()<=4);
            bpaResourceVoList.add(vo);
        }
        return bpaResourceVoList;
    }

    @Override
    public BPAResource findById(String Id) {
        return bpaResourceDao.findByResourceid(Id);
    }

    @Override
    public List<BPAResource> findByAttributeID() {
        return bpaResourceDao.findByAttributeID(0);
    }


    private String DateAddTimeWithString(Date startDate, String addTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        String[] times = addTime.split(":");
        Integer day = Integer.parseInt(times[0].split("\\.")[0]);
        Integer hour = Integer.parseInt(times[0].split("\\.")[1]);
        Integer mins = Integer.parseInt(times[1]);
        Integer seconds = Integer.parseInt(times[2]);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, mins);
        calendar.add(Calendar.SECOND, seconds);
        return sdf.format(calendar.getTime());
    }

    public boolean UpdateCacheResourceList() {
        if(GlobalUserUtil.socketChannels.size()<=0)return false;
        Map<String,Object[]> existMap=cacheUtil.getResourceFreshData();
        List<Object[]> datas=bpaResourceDao.findResourceFreshDatas();
        boolean needToupDate = false;
        Set<String> keySet = new HashSet<>(existMap.size());
        List<Object[]> updateList=new ArrayList<>();
        if (datas.size() != existMap.size()) {
            needToupDate = true;
        } else {
            for (Object[] obj : existMap.values()) {
                String key = GenerateKey(obj);
                keySet.add(key);
            }
            for (Object[] obj : datas) {
                String key = GenerateKey(obj);
                if (!keySet.contains(key)) {
                    needToupDate = true;
                }
                updateList.add(obj);
            }
        }
        if (needToupDate) {
            logger.info("检测到需要刷新Resource，并且通知前台");
            cacheUtil.updateResourceFrshData(updateList);
            WebSocketCommand command = new WebSocketCommand();
            command.setComponentName("resourceTable");
            command.setCommandType("fresh");
            for (Channel channel : GlobalUserUtil.socketChannels) {
                channel.writeAndFlush(new TextWebSocketFrame(JsonToObjectUtil.objectToJson(command)));
            }
        }
        return  needToupDate;
    }

    private String GenerateKey(Object[] obj){
        String key="";
        for (int i=0;i<obj.length;i++){
            key+=obj[i];
        }
        return key;
    }

    @Override
    public List<Object[]> findResourceFreshFlagData() {
       return   bpaResourceDao.findResourceFreshDatas();
    }

}
