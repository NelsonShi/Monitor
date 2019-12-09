package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.entity.BPASessionLogNonUnicode;
import com.blueprismserver.entity.QueryVo.BPASessionLogs;
import com.blueprismserver.entity.vo.BPAProcessVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sjlor on 2019/10/24.
 */
public interface IBPAProcess extends IService<BPAProcess> {
    List<BPAProcess> findByProcessType(String type);

    List<BPAProcess> findAll();

    List<BPAProcessVo> GenerateUnCompetedProcessVos(List<BPASessionLogs> uncompletedSessionList, Map<String, BPAProcess> processMap, Date queryDate,Date now);
}
