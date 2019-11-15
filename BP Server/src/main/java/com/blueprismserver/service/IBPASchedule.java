package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.*;
import com.blueprismserver.entity.vo.ScheduleVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2019/11/11.
 */
public interface IBPASchedule extends IService<BPASchedule> {
    List<BPASchedule> findUnRetireScheduleList();
    List<ScheduleVo> GenrateResourceScheduleVos(List<BPASchedule> scheduleList, Map<String,BPAScheduleTrigger> triggerMap,Map<String,BPAProcess> processMap,Map<String,BPAEnvironmentVar> environmentVarMap,
                                                Map<String,BPATask> taskMap, Map<String,BPATaskSession> taskSessionMap, Map<String,BPAResource> resourceNameMap);
}
