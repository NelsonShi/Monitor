package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IProcessDao;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.entity.BPASessionLogNonUnicode;
import com.blueprismserver.entity.QueryVo.BPASessionLogs;
import com.blueprismserver.entity.vo.BPAProcessVo;
import com.blueprismserver.service.IBPAProcess;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nelson on 2019/10/24.
 */
@Service
public class IBPAProcessImpl extends AbstractService<BPAProcess> implements IBPAProcess {
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   @Autowired
   private IProcessDao processDao;
    @Override
    public JpaRepository<BPAProcess,String> getRepository() {
        return processDao;
    }

    @Override
    public List<BPAProcess> findByProcessType(String type){
        return processDao.findByProcessType(type);
    }

    public  List<BPAProcess> findAll(){
        return processDao.findAll();
    }

    //根据session 以及session log 生成ProcessVo
    public List<BPAProcessVo> GenerateUnCompetedProcessVos(List<BPASessionLogs> uncompletedSessionList, Map<String,BPAProcess> processMap){
        List<BPAProcessVo> unCompletedVos=GenrateProcessVoWithUncompletedSession(uncompletedSessionList,processMap);
        return unCompletedVos;
    }

    private List<BPAProcessVo> GenrateProcessVoWithUncompletedSession(List<BPASessionLogs> uncompletedSessionList, Map<String,BPAProcess> processMap){
        Map<String,BPAProcessVo> voMap=new HashMap<>();
        for (BPASessionLogs sessionLog:uncompletedSessionList){
              String key=sessionLog.getProcessid();
              BPAProcessVo vo=voMap.get(key);
              BPAProcess process=processMap.get(key);
              if(process==null)continue;
              if(vo==null){
                  vo=new BPAProcessVo(process.getProcessid(),process.getName(),0,0);
                  voMap.put(vo.getProcessId(),vo);
              }
            SetProcessVoError(sessionLog.getSessionid(),sessionLog.getResult(),sessionLog.getEnddatetime(),vo,sessionLog.getStoprequested(),sessionLog.getStatusid());
        }
        List<BPAProcessVo> processVoList=new ArrayList<>(voMap.values());
        return processVoList;
    }


    /**
     *设置processVo error数据
     */
    private void SetProcessVoError(String stageName,String result,Date endTime,BPAProcessVo vo,Date requestStopTime,Integer statusid){
        Boolean definedError=StringUtils.isEmpty(stageName)?false:(stageName.toUpperCase().startsWith("BE:")||stageName.toUpperCase().startsWith("SE:"));
        Boolean undefinedError=StringUtils.isEmpty(result)?false:result.toUpperCase().contains("ERROR:");
        if(vo.getLastTime()==null||(endTime!=null&&vo.getLastTime().getTime()<=endTime.getTime())){
            vo.setLastTime(endTime);
        }
        if(definedError||undefinedError){
            vo.setErrorCount(vo.getErrorCount()==null?1:vo.getErrorCount()+1);
            if(undefinedError){
                vo.setUndefinedCount(vo.getUndefinedCount()==null?1:vo.getUndefinedCount()+1);
            }
        }
        if(vo.getErrorCount()<=0&&requestStopTime!=null){
            vo.setRequestedStoped(true);
            vo.setRequestedTime(sdf.format(requestStopTime));
        }
        if(vo.getErrorCount()<=0&&statusid==3){
            vo.setTerminated(true);
        }
    }


}
