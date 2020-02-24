package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPAGroupDao;
import com.fast.bpserver.dao.IBPAGroupProcessDao;
import com.fast.bpserver.entity.BPAEnvironmentVar;
import com.fast.bpserver.entity.BPAGroup;
import com.fast.bpserver.entity.BPAGroupProcess;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.service.IBPAGroup;
import com.fast.bpserver.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2020/1/13.
 */
@Service
public class IBPAGroupImpl extends AbstractService<BPAGroup> implements IBPAGroup {
    @Autowired
    private IBPAGroupDao ibpaGroupDao;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private IBPAGroupProcessDao groupProcessDao;
    @Override
    public JpaRepository<BPAGroup, String> getRepository() {
        return ibpaGroupDao;
    }

    @Override
    public List<BPAGroup> findProcessWithGroup() {
        Map<String,BPAProcess> customProcessMap=cacheUtil.getProcessList();
        Map<String,BPAEnvironmentVar> envMap= cacheUtil.getBPEnvVars();
        List<BPAGroup> groupList=ibpaGroupDao.findAll();
        Map<String,BPAGroup> groupMap=new HashMap<>(groupList.size());
        for (BPAGroup group:groupList){
            groupMap.put(group.getId(),group);
        }
        List<BPAGroupProcess> groupProcessList=groupProcessDao.findAll();
        List<BPAGroup> result=new ArrayList<>();
        for (BPAGroupProcess bpaGroupProcess:groupProcessList){
            BPAProcess process=customProcessMap.get(bpaGroupProcess.getProcessid());
            if(process==null)continue;
            BPAGroup group=groupMap.get(bpaGroupProcess.getGroupid());
            if(group.getProcessList()==null)group.setProcessList(new ArrayList<>());
            BPAEnvironmentVar env=envMap.get("RT - "+process.getName());
            if(env!=null){
                process.setRunTimeStr(env.getValue());
                process.setRunTimeMins(GetMinsWithTimeSpan(env.getValue()));
            }
            group.getProcessList().add(process);
        }
        for (BPAGroup group:groupMap.values()){
            if(group.getProcessList()!=null)result.add(group);
        }
        return result;
    }


    private Integer GetMinsWithTimeSpan(String value){
        Integer mins=0;
        try {
            String[] splits=value.split(":");
            String[] dayAndHour=splits[0].split("\\.");
            mins=Integer.parseInt(dayAndHour[0])*24*60+Integer.parseInt(dayAndHour[1])*60+Integer.parseInt(splits[1]);
            return mins;
        }catch (Exception ex){
            return  -1;
        }
    }
}
