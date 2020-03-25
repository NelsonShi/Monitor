package com.fast.monitorserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.entity.BPAResource;
import com.fast.monitorserver.dao.IResourceIpRefDao;
import com.fast.monitorserver.entity.ResourceIpRef;
import com.fast.monitorserver.schedules.ResourcePcClientHeartBeat;
import com.fast.monitorserver.service.IResourceIpRef;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 2020/1/20.
 */
@Service
public class ResourceIpRefImpl extends AbstractService<ResourceIpRef> implements IResourceIpRef{
    private static Logger log= LoggerFactory.getLogger(ResourceIpRefImpl.class);
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

    @Override
    public Object[] findLastUpdate() {
        return resourceIpRefDao.findMaxUpdated();
    }

    @Override
    public void SyncFromBp(List<BPAResource> resourceList) {
        List<ResourceIpRef> refList=resourceIpRefDao.findAll();
        Map<String,ResourceIpRef> refMap=new HashMap<>(refList.size());
        for (ResourceIpRef ref:refList){
            String key=ref.getResourceId();
            refMap.put(key,ref);
        }
        List<ResourceIpRef> saveList=new ArrayList<>();
        for(BPAResource resource:resourceList){
            String key=resource.getResourceid();
            ResourceIpRef resourceIpRef=refMap.get(key);
            if(resourceIpRef==null||resourceIpRef.getLastUpdated()==null||resourceIpRef.getLastUpdated().getTime()<resource.getLastupdated().getTime()){
                saveList.add(createdRef(resource,resourceIpRef));
            }

        }
        if(saveList.size()>0){
            log.info("Resource ip ref Sync form Bp: "+saveList.size());
            resourceIpRefDao.saveAll(saveList);
        }
    }

    private ResourceIpRef createdRef(BPAResource resource,ResourceIpRef resourceIpRef){
        if(resourceIpRef==null){
            resourceIpRef=new ResourceIpRef();
            String resourceName= getName(resource.getName());
            resourceIpRef.setResourceName(resourceName);
            resourceIpRef.setResourceId(resource.getResourceid());
            resourceIpRef.setPort("8181");
            resourceIpRef.setBpName(resource.getName());
            resourceIpRef.setFqdn(resource.getFQDN());
            resourceIpRef.setStatus(1);
            resourceIpRef.setLastUpdated(resource.getLastupdated());
        }else {
            resourceIpRef.setLastUpdated(resource.getLastupdated());
        }
        return resourceIpRef;
    }

    private String getName(String name){
        try{
            if(name==null||name.length()<=0){
                return "";
            }
            if (name.contains(".")){
                return name.split("\\.")[0];
            }else {
                return name;
            }}catch (Exception ex){
            return name;
        }
    }
}
