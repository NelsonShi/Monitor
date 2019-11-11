package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPAResourceDao;
import com.blueprismserver.entity.*;
import com.blueprismserver.entity.vo.BPAResourceVo;
import com.blueprismserver.entity.vo.ComputerData;
import com.blueprismserver.service.IBPAResource;
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
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Autowired
    private IBPAResourceDao bpaResourceDao;
    @Override
    public JpaRepository<BPAResource,String> getRepository() {
        return bpaResourceDao;
    }

    @Override
    public List<BPAResource> findAll(){
        return bpaResourceDao.findAll();
    }

    @Override
    public List<BPAResourceVo> GenrateListWithResourceAndUser(Map<String,ComputerData> computerDataMap, Map<String,BPAUser> userMap, List<BPAResource> resourceList,
                                                              Map<String,BPASession> sessionMap, Map<String,BPAProcess> processMap, Map<String,BPAEnvironmentVar> envMap){
        List<BPAResourceVo> bpaResourceVoList=new ArrayList<>();
        for (BPAResource resource:resourceList){
            BPAResourceVo vo=new BPAResourceVo();
            vo.setResourceid(resource.getResourceid());
            vo.setName(resource.getName());
            BPASession session=sessionMap.get(resource.getResourceid());
            if(session!=null){
                BPAProcess p=processMap.get(session.getProcessid());
                if(p!=null){
                    vo.setProcessStatus(session.getStatusid());
                    vo.setProcessName(p.getName());
                    if(session.getStatusid()==0||session.getStatusid()==1||session.getStatusid()==2){
                        String timeSlot=sdf.format(session.getStartdatetime());
                        BPAEnvironmentVar enVar=envMap.get("RT - "+p.getName());
                        if(enVar==null){
                            timeSlot+="--- Undefined";
                        }else {
                            timeSlot+=("---"+DateAddTimeWithString(session.getStartdatetime(),enVar.getValue()));
                        }
                        vo.setTimeSlot(timeSlot);
                    }
                    else {
                        vo.setTimeSlot("");
                    }
                }else {
                    vo.setProcessStatus(3);
                    vo.setProcessName("error:can not find process");
                    vo.setTimeSlot("");
                }
            }else {
                vo.setProcessStatus(0);
                vo.setProcessName("");
                vo.setTimeSlot("");
            }
            vo.setLastupdated(sdf.format(resource.getLastupdated()));
            vo.setAttributeID(resource.getAttributeID());
            vo.setFQDN(resource.getFQDN());
            vo.setUserName(resource.getUserID()==null?"":userMap.get(resource.getUserID())==null?"":userMap.get(resource.getUserID()).getUsername());
            vo.setStatusid(resource.getStatusid());
            vo.setDisplayStatus(resource.getDisplayStatus());
            if(computerDataMap==null||computerDataMap.isEmpty()){
                vo.setBotIp("Netty error:404");
                vo.setIsNettyConnected(0);
            }else {
                boolean hasNettyConnected=computerDataMap.get(vo.getName())!=null;
                vo.setBotIp(hasNettyConnected?computerDataMap.get(vo.getName()).getBotIP():"");
                vo.setIsNettyConnected(hasNettyConnected?1:0);
            }
            bpaResourceVoList.add(vo);
        }
        return bpaResourceVoList;
    }


    private String DateAddTimeWithString(Date startDate,String addTime){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(startDate);
        String[] times=addTime.split(":");
        Integer day=Integer.parseInt(times[0].split("\\.")[0]);
        Integer hour=Integer.parseInt(times[0].split("\\.")[1]);
        Integer mins=Integer.parseInt(times[1]);
        Integer seconds=Integer.parseInt(times[2]);
        calendar.add(Calendar.DAY_OF_YEAR,day);
        calendar.add(Calendar.HOUR_OF_DAY,hour);
        calendar.add(Calendar.MINUTE,mins);
        calendar.add(Calendar.SECOND,seconds);
        return sdf.format(calendar.getTime());
    }
}
