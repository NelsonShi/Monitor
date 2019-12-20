package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.vo.BPAProcessVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sjlor on 2019/10/24.
 */
public interface IBPAProcess extends IService<BPAProcess> {
    List<BPAProcess> findByProcessType(String type);

    List<BPAProcess> findAll();

    List<BPAProcessVo> GenerateUnCompetedProcessVos(List<BPASessionLogs> uncompletedSessionList, Map<String, BPAProcess> processMap, Date queryDate, Date now,Integer timeZone);
}
