package com.fast.bpserver.resource;

import com.fast.bpserver.base.BaseResource;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.postEntity.ScheduleVoParams;
import com.fast.bpserver.entity.vo.BPAProcessVo;
import com.fast.bpserver.entity.vo.BPAResourceVo;
import com.fast.bpserver.entity.vo.ComputerData;
import com.fast.bpserver.entity.vo.ScheduleVo;
import com.fast.bpserver.service.*;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.TimeZoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nelson on 2019/10/24.
 */
@RestController
@RequestMapping("/process")
public class ProcessResource extends BaseResource {

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private IBPASession bpaSessionService;
    @Autowired
    private IBPASession ibpaSessionService;
    @Autowired
    private IBPAProcess processService;



    @RequestMapping("/processList")
    public List<BPAProcess> EmployeeSize(){
        List<BPAProcess> processListList=processService.findAll();
        return processListList;
    }


    @RequestMapping("/recentlySession")
    public List<BPASession> recentlySession(){
        List<BPASession> list=bpaSessionService.recentlySession();
        return list;
    }

    @RequestMapping("/errorProcessVos")
    public List<BPAProcessVo> finderrorProcessVos(@RequestParam Integer requestTimeZone){
        Date dateNow=new Date();
        int timeZone=  TimeZone.getDefault().getOffset(dateNow.getTime())/(1000*60*60);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateNow);
        calendar.add(Calendar.HOUR_OF_DAY,-timeZone);
        dateNow=calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date  querydate=calendar.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<BPASessionLogs> list= ibpaSessionService.findErrorSessionAndLogs(sdf.format(querydate));
        List<BPAProcessVo> voList=processService.GenerateUnCompetedProcessVos(list,cacheUtil.getProcessList(),querydate,dateNow,requestTimeZone);
        for (BPAProcessVo vo:voList){
            vo.InitMessage();
            vo.setLastTimeStr(vo.getLastTime()==null?"":sdf.format(vo.getLastTime()));
        }
        return voList;
    }

}
