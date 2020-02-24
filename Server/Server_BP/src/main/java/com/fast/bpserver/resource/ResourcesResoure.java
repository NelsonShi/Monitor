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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nelson on 2019/11/11.
 */
@RestController
@RequestMapping("/resource")
public class ResourcesResoure extends BaseResource {

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private IBPAUser bpaUserService;
    @Autowired
    private IBPAResource bpaResourceService;
    @Autowired
    private IBPASession bpaSessionService;
    @Autowired
    private IBPASchedule ibpaScheduleService;


    @RequestMapping("/resourceList")
    public List<BPAResourceVo> Resourcelist(@RequestParam Integer requestTimeZone){
        return  GenrateBPAResourceVo(requestTimeZone);
    }

    @RequestMapping("/resourceListForWeb")
    public List<BPAResourceVo> ResourcelistForWeb(@RequestParam Integer requestTimeZone){
        return  GenrateBPAResourceVo(requestTimeZone);
    }

    @RequestMapping("/bots")
    public List<ComputerData> BotsList(){
        List<ComputerData> computerDataList=cacheUtil.getAllBotList();
        return computerDataList;
    }

    @RequestMapping("/bpaStatus")
    public List<BPAStatus> GetStatus(){
        return cacheUtil.getStatus();
    }


    @RequestMapping(value = "/scheduleVos",method = RequestMethod.POST)
    public List<ScheduleVo> GetResourceSchedules(@RequestBody ScheduleVoParams scheduleVoParams){
//        List<BPASchedule> scheduleList=ibpaScheduleService.findUnRetireScheduleList();
//        List<BPAScheduleTrigger> bpaScheduleTriggerList=scheduleTriggerSevice.findAll();
//        List<BPATask> taskList=taskService.findAll();
//        List<BPATaskSession> taskSessionList=taskSessionService.findAll();
//        List<BPAResource> resourceList=cacheUtil.getResourceList();
//        Map<String,BPAScheduleTrigger> triggerMap=new HashMap<>();
//        Map<String,BPATask> taskMap=new HashMap<>();
//        Map<String,BPATaskSession> taskSessionMap=new HashMap<>();
//        Map<String,BPAResource> resourceMap=new HashMap<>();
//        for (BPAScheduleTrigger trigger:bpaScheduleTriggerList){
//            triggerMap.put(trigger.getScheduleid().toString(),trigger);
//        }
//        for (BPATask task:taskList){
//            taskMap.put(task.getScheduleid().toString(),task);
//        }
//        for (BPATaskSession taskSession:taskSessionList){
//            taskSessionMap.put(taskSession.getTaskid().toString(),taskSession);
//        }
//        for (BPAResource resource:resourceList){
//            resourceMap.put(resource.getName(),resource);
//        }
//        Map<String,BPAProcess> processMap=cacheUtil.getProcessList();
//        Map<String,BPAEnvironmentVar> envarMap=cacheUtil.getBPEnvVars();
        TranslateTimeZone(scheduleVoParams);
//        return ibpaScheduleService.GenrateResourceScheduleVos(scheduleList,triggerMap,processMap,envarMap,taskMap,taskSessionMap,resourceMap,scheduleVoParams.getStartTime(),scheduleVoParams.getEndTime(),scheduleVoParams.getRequestTimeZone());
        return ibpaScheduleService.GenrateResourceScheduleVosWithTimeSpan(scheduleVoParams.getStartTime(),scheduleVoParams.getEndTime(),scheduleVoParams.getRequestTimeZone());
    }

    private void TranslateTimeZone(ScheduleVoParams scheduleVoParams){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(scheduleVoParams.getDateNow());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.HOUR_OF_DAY,scheduleVoParams.getRequestTimeZone());
        scheduleVoParams.setStartTime(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        scheduleVoParams.setEndTime(calendar.getTime());
        scheduleVoParams.setRequestTimeZone(scheduleVoParams.getRequestTimeZone());
    }



    private List<BPAResourceVo> GenrateBPAResourceVo(Integer requestTimeZone){
        List<BPAResource> resourceList=bpaResourceService.findByAttributeID();
        List<BPAUser> userList=bpaUserService.getAll();
        List<ComputerData> computerDataList=cacheUtil.getAllBotList();
        List<BPASession> runningInfo=bpaSessionService.recentlySession();
        Map<String,ComputerData> computerDataMap=new HashMap<>();
        Map<String,BPAUser> userMap=new HashMap<>();
        Map<String,BPASession> runningInfoMap=new HashMap<>();
        for (ComputerData cd:computerDataList){
            computerDataMap.put(cd.getBotName(),cd);
        }
        for (BPAUser user:userList){
            userMap.put(user.getUserId(),user);
        }
        for (BPASession info:runningInfo){
            runningInfoMap.put(info.getRunningresourceid(),info);
        }
        Map<String,BPAProcess> processMap=cacheUtil.getProcessList();
        Map<String,BPAEnvironmentVar> envarMap=cacheUtil.getBPEnvVars();
        return bpaResourceService.GenrateListWithResourceAndUser(computerDataMap,userMap,resourceList,runningInfoMap,processMap,envarMap,requestTimeZone);
    }

}
