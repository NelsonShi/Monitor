package com.fast.monitorserver.resource;

import com.fast.bpserver.base.BaseResource;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.postEntity.BotCommand;
import com.fast.bpserver.entity.postEntity.BotCommandResult;
import com.fast.bpserver.entity.postEntity.SessionParams;
import com.fast.bpserver.entity.vo.ScheduleVo;
import com.fast.bpserver.nettyServer.GlobalUserUtil;
import com.fast.bpserver.service.*;
import com.fast.bpserver.utils.CacheUtil;
import com.fast.bpserver.utils.JsonToObjectUtil;
import com.fast.bpserver.utils.TimeZoneUtil;
import com.fast.monitorserver.RSListenerClient.RsPcClient;
import com.fast.monitorserver.entity.ResourceIpRef;
import com.fast.monitorserver.entity.vo.*;
import com.fast.monitorserver.service.IResourceIpRef;
import com.fast.monitorserver.service.other.TimeAvailableResourceService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nelson on 2019/12/11.
 */
@RestController
@RequestMapping("/operation")
public class OperationResource extends BaseResource {
    @Autowired
    private IBPASession bpaSessionService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private IBPAUser userService;
    @Autowired
    private IBPASchedule ibpaScheduleService;
    @Autowired
    private IBPAGroup ibpaGroupService;
    @Autowired
    private TimeAvailableResourceService timeAvailableResourceService;
    @Autowired
    private IResourceIpRef resourceIpRefService;
    @Value("${server.props.DBtimeZone}")
    private Integer DBTimeZone;

    private static final Logger log = LoggerFactory.getLogger(OperationResource.class);

    @RequestMapping(value = "/command", method = RequestMethod.POST)
    public BotCommandResult Command(@RequestBody BotCommand vo, HttpServletRequest request) {
        Map<String, Channel> chanelMap = GlobalUserUtil.tcpChannelMap;
        Channel channel = chanelMap.get(vo.getIp());
        BotCommandResult br;
        if (channel == null) {
            log.info("can not find channel, IP:::" + vo.getIp());
            br = new BotCommandResult(0, "false");
        } else {
            channel.write(JsonToObjectUtil.objectToJson(vo));
            channel.flush();
            br = new BotCommandResult(1, "success");
        }
        return br;
    }


    @RequestMapping(value = "/pending", method = RequestMethod.POST)
    public PendingProcessRes PendingProcess(@RequestBody PendingProcessPara para) {
        int sysTimeZone = TimeZone.getDefault().getOffset(new Date().getTime()) / (1000 * 60 * 60);
        BPAUser user = userService.findById(para.getUserId());
        Map<String, BPAProcess> processeMap = cacheUtil.getProcessList();
        BPAProcess process = processeMap.get(para.getProcessId());
        Map<String, ResourceIpRef> resourceIpRefMap = resourceIpRefService.findResourceIpRefMap();
        PendingProcessRes result = new PendingProcessRes();
        for (String resourceId : para.getResourceIds()) {
            ResourceIpRef rir = resourceIpRefMap.get(resourceId);
            Channel channel = RsPcClient.tcpClientMap.get(rir.getConnectIp());
            bpaSessionService.PendingProcess(user, process, sysTimeZone - DBTimeZone, channel);
        }
        return result;
    }

    @RequestMapping(value = "/startSession",method = RequestMethod.POST)
    public void StartSession(@RequestBody ControlSessionPara para) {
        ResourceIpRef ref=resourceIpRefService.findById(para.getResourceId());
        if(ref==null)return;
        Channel channel = RsPcClient.tcpClientMap.get(ref.getConnectIp());
        channel.writeAndFlush("startp\r");
    }

    @RequestMapping(value = "/findSessions",method = RequestMethod.POST)
    public List<BPASession> findSessions(@RequestBody SessionParams para){
        List<BPASession> list=bpaSessionService.findSessionWithParams(para);
        List<BPAResource> resources=cacheUtil.getResourceList();
        Map<String,BPAProcess> processMap=cacheUtil.getProcessList();
        List<BPAUser> users=userService.findAll();
        Map<String,BPAUser> userMap=new HashMap<>(users.size());
        for (BPAUser user:users){
            userMap.put(user.getUserId(),user);
        }
        Map<String,BPAResource> resourceMap=new HashMap<>(resources.size());
        for(BPAResource resource:resources){
            resourceMap.put(resource.getResourceid(),resource);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yy/MM/DD HH:mm:ss");
        for (BPASession session:list){
            session.SetDispalyNames(processMap.get(session.getProcessid()).getName(),resourceMap.get(session.getStarterresourceid()).getName(),userMap.get(session.getStarteruserid()).getUserName(),sdf);
        }
        return list;
    }

    @RequestMapping(value = "/processWithGroup")
    public List<BPAGroup> GetProcessWithGroup() {
        List<BPAGroup> groupList = ibpaGroupService.findProcessWithGroup();
        return groupList;
    }

    @RequestMapping(value = "/pendingSessions")
    public  List<BPASession> findPendingSessions(){
        List<BPASession> sessionList=bpaSessionService.findAllPendingSessions();
        List<BPAResource> resources=cacheUtil.getResourceList();
        Map<String,BPAProcess> processMap=cacheUtil.getProcessList();
        List<BPAUser> users=userService.findAll();
        Map<String,BPAUser> userMap=new HashMap<>(users.size());
        for (BPAUser user:users){
            userMap.put(user.getUserId(),user);
        }
        Map<String,BPAResource> resourceMap=new HashMap<>(resources.size());
        for(BPAResource resource:resources){
            resourceMap.put(resource.getResourceid(),resource);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yy/MM/DD HH:mm:ss");
        for (BPASession session:sessionList){
            session.SetDispalyNames(processMap.get(session.getProcessid()).getName(),resourceMap.get(session.getStarterresourceid()).getName(),userMap.get(session.getStarteruserid()).getUserName(),sdf);
        }
        return  sessionList;
    }

    @RequestMapping(value = "/availableResource", method = RequestMethod.POST)
    public List<TimeAvailableResourceVo> GetResourceTimeAvailable(@RequestBody TimeAvailabePara para) {
        Date formateDate = TimeZoneUtil.formateDateToZone(para.getTimeStart(), -para.getTimeZone());
        //默认获取一小时以内的schedule
        Date endDate = new Date(formateDate.getTime() + 60 * 60 * 1000);
        List<ScheduleVo> scheduleVos = ibpaScheduleService.GenrateResourceScheduleVosWithTimeSpan(formateDate, endDate, para.getTimeZone());
        return timeAvailableResourceService.GenerateRs(scheduleVos, para.getTimeSpan());
    }

}
