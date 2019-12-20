package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPAScheduleDao;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.vo.ScheduleTimeSlot;
import com.fast.bpserver.entity.vo.ScheduleVo;
import com.fast.bpserver.service.IBPASchedule;
import com.fast.bpserver.ScheduleTimeSlotModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sjlor on 2019/11/11.
 */
@Service
public class IBPAScheduleImpl extends AbstractService<BPASchedule> implements IBPASchedule {
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    @Autowired
    private IBPAScheduleDao scheduleDao;

    @Override
    public JpaRepository<BPASchedule, String> getRepository() {
        return scheduleDao;
    }


    public List<BPASchedule> findUnRetireScheduleList() {
        return scheduleDao.findByRetired(false);
    }

    public List<ScheduleVo> GenrateResourceScheduleVos(List<BPASchedule> scheduleList, Map<String, BPAScheduleTrigger> triggerMap, Map<String, BPAProcess> processMap, Map<String, BPAEnvironmentVar> environmentVarMap,
                                                       Map<String, BPATask> taskMap, Map<String, BPATaskSession> taskSessionMap, Map<String, BPAResource> resourceNameMap,Date startTime,Date endTime,Integer timeZone) {
        Map<String, ScheduleVo> voMap = new HashMap<>();

        for (BPASchedule schedule : scheduleList) {
            BPAScheduleTrigger refTrigger = triggerMap.get(schedule.getId().toString());
            BPATask refTask = taskMap.get(schedule.getId().toString());
            if (refTask == null || refTrigger == null) continue;
            BPATaskSession refTaskSession = taskSessionMap.get(refTask.getId().toString());
            ScheduleVo vo = voMap.get(refTaskSession.getResourcename());
            String runTime = environmentVarMap.get("RT - " + processMap.get(refTaskSession.getProcessid()).getName()).getValue();
            ScheduleTimeSlotModule module = createModule(startTime, endTime, refTrigger, runTime, processMap.get(refTaskSession.getProcessid()).getName(), refTrigger.getId().toString(),timeZone);
            if (vo == null && module.getNeedToStart()) {
                vo = new ScheduleVo();
                vo.setTimeSpan(new Long((endTime.getTime() - startTime.getTime()) / (1000 * 60)).intValue());
                vo.setResourcename(refTaskSession.getResourcename());
                vo.setResourceid(resourceNameMap.get(refTaskSession.getResourcename()).getResourceid());
                vo.setTimeSlots(module.getScheduleSlotList());
                voMap.put(vo.getResourcename(), vo);
            } else {
                CombineTimeSlots(vo, module);
            }
        }
        List<ScheduleVo> voList = new ArrayList<>();
        for (ScheduleVo vo : voMap.values()) {
            //按从小到大排序
            vo.SortTimeSlots();
            //设置UI 所需的左边距
            vo.SetSlotMargins();
            voList.add(vo);
        }
        return voList;
    }

    private void CombineTimeSlots(ScheduleVo vo, ScheduleTimeSlotModule module) {
        if (vo == null || !module.getNeedToStart()) return;
        if (vo.getTimeSlots() == null && module.getNeedToStart()) {
            vo.setTimeSlots(module.getScheduleSlotList());
        }
        if (vo.getTimeSlots() != null && module.getNeedToStart() && module.getScheduleSlotList() != null) {
            for (ScheduleTimeSlot sts : module.getScheduleSlotList()) {
                vo.getTimeSlots().add(sts);
            }
        }
    }

    private ScheduleTimeSlotModule createModule(Date inputbegin, Date inputEnd, BPAScheduleTrigger trigger, String runtime, String processName, String trggerName,Integer timeZone) {
        String[] times = runtime.split(":");
        Integer day = Integer.parseInt(times[0].split("\\.")[0]);
        Integer hour = Integer.parseInt(times[0].split("\\.")[1]);
        Integer mins = Integer.parseInt(times[1]);
        long runSeconds = day * 24 * 60 * 60 + hour * 60 * 60 + mins * 60;
        ScheduleTimeSlotModule module = new ScheduleTimeSlotModule(inputbegin, inputEnd, trigger.getStartdate(), runSeconds, trigger.getPeriod(), trigger.getUnittype(),trigger.getStartpoint(),trigger.getEndpoint(),timeZone);
        module.GenrateScheduleList(processName, sdf, trggerName);
        return module;
    }


}
