package com.blueprismserver.resoruce;

import com.blueprismserver.entity.BPAResource;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.entity.BPAUser;
import com.blueprismserver.entity.vo.BPAResourceVo;
import com.blueprismserver.entity.vo.ComputerData;
import com.blueprismserver.service.IBPAResource;
import com.blueprismserver.service.IBPASession;
import com.blueprismserver.service.IBPAUser;
import com.blueprismserver.service.IBPAProcess;
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
public class ProcessResource {
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

    @RequestMapping("/start")
    public void StartProcess(){
        List<BPAProcess> customBpaProcessList=processService.findByProcessType("P");
        List<BPAUser> userList=bpaUserService.getAll();
        List<BPAResource> resourceList=bpaResourceService.findAll();
    }

    private List<BPAResourceVo> GenrateBPAResourceVo(){
        List<BPAResource> resourceList=bpaResourceService.findAll();
        List<BPAUser> userList=bpaUserService.getAll();
        List<ComputerData> computerDataList=cacheUtil.getAllBotList();
        Map<String,ComputerData> computerDataMap=new HashMap<>();
        Map<String,BPAUser> userMap=new HashMap<>();
        for (ComputerData cd:computerDataList){
            computerDataMap.put(cd.getBotName(),cd);
        }
        for (BPAUser user:userList){
            userMap.put(user.getUserid(),user);
        }
        return bpaResourceService.GenrateListWithResourceAndUser(computerDataMap,userMap,resourceList);
    }
}
