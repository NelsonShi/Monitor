package com.blueprismserver.resoruce;

import com.blueprismserver.base.BaseResource;
import com.blueprismserver.entity.*;
import com.blueprismserver.entity.vo.BPAResourceVo;
import com.blueprismserver.entity.vo.ComputerData;
import com.blueprismserver.entity.vo.ScheduleVo;
import com.blueprismserver.service.*;
import com.blueprismserver.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2019/10/24.
 */
@RestController
@RequestMapping("/process")
public class ProcessResource extends BaseResource {
    @Autowired
    private IBPAProcess processService;
    @Autowired
    private IBPAUser bpaUserService;
    @Autowired
    private IBPAResource bpaResourceService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private IBPASession bpaSessionService;
    @Autowired
    private IBPASchedule ibpaScheduleService;
    @Autowired
    private IBPAScheduleTrigger scheduleTriggerSevice;
    @Autowired
    private IBPATask taskService;
    @Autowired
    private IBPATaskSession taskSessionService;

    @RequestMapping("/hello")
    public String hello(){
        return "helloWord";
    }

    @RequestMapping("/processList")
    public List<BPAProcess> EmployeeSize(){
        List<BPAProcess> processListList=processService.findAll();
        return processListList;
    }

    @RequestMapping("/resourceList")
    public List<BPAResourceVo> Resourcelist(){
        return  GenrateBPAResourceVo();
    }

    /*
     用于定期刷新
    * */
    @RequestMapping("/resourceListForWeb")
    public List<BPAResourceVo> ResourcelistForWeb(){
       return  GenrateBPAResourceVo();
    }

    @RequestMapping("/bots")
    public List<ComputerData> BotsList(){
        List<ComputerData> computerDataList=cacheUtil.getAllBotList();
        return computerDataList;
    }

    @RequestMapping("/recentlySession")
    public List<BPASession> recentlySession(){
        List<BPASession> list=bpaSessionService.recentlySession();
        return list;
    }

    @RequestMapping("/scheduleVos")
    public List<ScheduleVo> GetResourceSchedules(){
        List<BPASchedule> scheduleList=ibpaScheduleService.findUnRetireScheduleList();
        List<BPAScheduleTrigger> bpaScheduleTriggerList=scheduleTriggerSevice.findAll();
        List<BPATask> taskList=taskService.findAll();
        List<BPATaskSession> taskSessionList=taskSessionService.findAll();
        List<BPAResource> resourceList=cacheUtil.getResourceList();
        Map<String,BPAScheduleTrigger> triggerMap=new HashMap<>();
        Map<String,BPATask> taskMap=new HashMap<>();
        Map<String,BPATaskSession> taskSessionMap=new HashMap<>();
        Map<String,BPAResource> resourceMap=new HashMap<>();
        for (BPAScheduleTrigger trigger:bpaScheduleTriggerList){
            triggerMap.put(trigger.getScheduleid().toString(),trigger);
        }
        for (BPATask task:taskList){
            taskMap.put(task.getScheduleid().toString(),task);
        }
        for (BPATaskSession taskSession:taskSessionList){
            taskSessionMap.put(taskSession.getTaskid().toString(),taskSession);
        }
        for (BPAResource resource:resourceList){
            resourceMap.put(resource.getName(),resource);
        }
        Map<String,BPAProcess> processMap=cacheUtil.getProcessList();
        Map<String,BPAEnvironmentVar> envarMap=cacheUtil.getBPEnvVars();
        return ibpaScheduleService.GenrateResourceScheduleVos(scheduleList,triggerMap,processMap,envarMap,taskMap,taskSessionMap,resourceMap);
    }

    private List<BPAResourceVo> GenrateBPAResourceVo(){
        List<BPAResource> resourceList=bpaResourceService.findAll();
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
            userMap.put(user.getUserid(),user);
        }
        for (BPASession info:runningInfo){
            runningInfoMap.put(info.getRunningresourceid(),info);
        }
        Map<String,BPAProcess> processMap=cacheUtil.getProcessList();
        Map<String,BPAEnvironmentVar> envarMap=cacheUtil.getBPEnvVars();
        return bpaResourceService.GenrateListWithResourceAndUser(computerDataMap,userMap,resourceList,runningInfoMap,processMap,envarMap);
    }
}
