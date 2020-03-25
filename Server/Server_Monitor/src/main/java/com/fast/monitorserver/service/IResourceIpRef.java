package com.fast.monitorserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAResource;
import com.fast.monitorserver.entity.ResourceIpRef;

import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2020/1/20.
 */
public interface IResourceIpRef extends IService<ResourceIpRef> {
    ResourceIpRef findById(String id);
    Map<String,ResourceIpRef> findResourceIpRefMap();
    Object[] findLastUpdate();
    void SyncFromBp(List<BPAResource> resourceList);
}
