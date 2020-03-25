package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.*;
import com.fast.bpserver.entity.vo.BPAResourceVo;
import com.fast.bpserver.entity.vo.ComputerData;

import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2019/10/24.
 */
public interface IBPAResource extends IService<BPAResource> {
    List<BPAResource> findAll();
    List<BPAResource> findByAttributeID();
    boolean UpdateCacheResourceList();
    List<Object[]> findResourceFreshFlagData();
    List<BPAResourceVo> GenrateListWithResourceAndUser(Map<String, ComputerData> computerDataMap, Map<String, BPAUser> userMap,
                                                       List<BPAResource> resourceList, Map<String, BPASession> sessionMap, Map<String, BPAProcess> processMap,
                                                       Map<String, BPAEnvironmentVar> envMap,Integer timeSpan);
    BPAResource findById(String Id);
    Object[] findMaxUpdatedItem();
}
