package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.*;
import com.blueprismserver.entity.vo.BPAResourceVo;
import com.blueprismserver.entity.vo.ComputerData;

import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2019/10/24.
 */
public interface IBPAResource extends IService<BPAResource> {
    List<BPAResource> findAll();
    List<BPAResourceVo> GenrateListWithResourceAndUser(Map<String,ComputerData> computerDataMap, Map<String,BPAUser> userMap,
                                                       List<BPAResource> resourceList, Map<String,BPASession> sessionMap, Map<String,BPAProcess> processMap,
                                                       Map<String,BPAEnvironmentVar> envMap);
}
