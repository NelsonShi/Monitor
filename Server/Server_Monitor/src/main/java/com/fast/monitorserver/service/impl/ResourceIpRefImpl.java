package com.fast.monitorserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.monitorserver.dao.IResourceIpRefDao;
import com.fast.monitorserver.entity.ResourceIpRef;
import com.fast.monitorserver.service.IResourceIpRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2020/1/20.
 */
@Service
public class ResourceIpRefImpl extends AbstractService<ResourceIpRef> implements IResourceIpRef{
    @Autowired
    private IResourceIpRefDao resourceIpRefDao;
    @Override
    public JpaRepository<ResourceIpRef, String> getRepository() {
        return resourceIpRefDao;
    }

    @Override
    public ResourceIpRef findById(String id) {
        return resourceIpRefDao.findByResourceId(id);
    }

    @Override
    public Map<String, ResourceIpRef> findResourceIpRefMap() {
        List<ResourceIpRef> resourceIpRefs=resourceIpRefDao.findAll();
        Map<String,ResourceIpRef> map=new HashMap<>(resourceIpRefs.size());
        for(ResourceIpRef rir:resourceIpRefs){
            map.put(rir.getResourceId(),rir);
        }
        return map;
    }
}
