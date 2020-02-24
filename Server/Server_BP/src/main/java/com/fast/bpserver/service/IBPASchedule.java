package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.vo.ScheduleVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2019/11/11.
 */
public interface IBPASchedule extends IService<BPASchedule> {
    List<BPASchedule> findUnRetireScheduleList();
    List<ScheduleVo> GenrateResourceScheduleVosWithTimeSpan(Date startTime,Date endTime,Integer timeZone);
    List<ScheduleVo> GenrateResourceScheduleVos(List<BPASchedule> scheduleList, Map<String, BPAScheduleTrigger> triggerMap, Map<String, BPAProcess> processMap, Map<String, BPAEnvironmentVar> environmentVarMap,
                                                Map<String, BPATask> taskMap, Map<String, BPATaskSession> taskSessionMap, Map<String, BPAResource> resourceNameMap, Date startTime,Date endTime,Integer timeZone);
}
