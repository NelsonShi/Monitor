package com.fast.bpserver.utils;

import com.fast.bpserver.CacheKey;
import com.fast.bpserver.dao.IBPASessionDao;
import com.fast.bpserver.entity.BPAEnvironmentVar;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.entity.BPAResource;
import com.fast.bpserver.entity.vo.ComputerData;
import com.fast.bpserver.service.IBPAEnviornmentVar;
import com.fast.bpserver.service.IBPAProcess;
import com.fast.bpserver.service.IBPAResource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sjlor on 2019/10/24.
 */
@Service
public class CacheUtil {
    @Autowired
    private IBPAResource resourceService;
    @Autowired
    private IBPAEnviornmentVar enviornmentVarService;
    @Autowired
    private IBPAProcess ibpaProcessService;


    public void InitCache() {
        initResources();
        InitConnectBot();
        initEnvironmentVar();
        initProcess();
        InitResourceFreshData();
    }

    public void InitConnectBot() {
        Map<String, ComputerData> computerDataMap = new HashMap<>(1024);
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.BotCacheKey, computerDataMap);
        cache.put(element);
    }
    public void InitResourceFreshData(){
        List<Object[]> datas=resourceService.findResourceFreshFlagData();
        Map<String,Object[]> dataMap=new HashMap<>(1024);
        for (Object[] obj:datas){
            dataMap.put(obj[0].toString(),obj);
        }
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.ResourceFreshData, dataMap);
        cache.put(element);
    }

    public Map<String,Object[]> getResourceFreshData(){
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.ResourceFreshData);
        if(element==null||element.getObjectValue()==null||element.isExpired()){
            InitResourceFreshData();
            element = cache.get(CacheKey.ResourceFreshData);
        }
        Map<String,Object[]> map=(Map<String,Object[]>)element.getObjectValue();
        return map;
    }

    public void updateResourceFrshData(List<Object[]> newList){
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.ResourceFreshData);
        if(element==null||element.getObjectValue()==null||element.isExpired()){
            InitResourceFreshData();
        }else {
            Map<String,Object[]> map=(Map<String,Object[]>)element.getObjectValue();
            map.clear();
            for (Object[] obj:newList){
                map.put(obj[0].toString(),obj);
            }
        }
    }


    public void initResources() {
        List<BPAResource> resourceList = resourceService.findByAttributeID();
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Map<String, BPAResource> resourceMap = new HashMap<>(1024);
        for (BPAResource resource : resourceList) {
            resourceMap.put(resource.getResourceid(), resource);
        }
        Element element = new Element(CacheKey.ResourcesCacheKey, resourceMap);
        cache.put(element);
    }

    public void UpdateResourceList(List<BPAResource> list) {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.ResourcesCacheKey);
        if (element.isExpired() || element == null || element.getObjectValue() == null) {
            initResources();
        } else {
            Map<String, BPAResource> existmap = (Map<String, BPAResource>) element.getObjectValue();
            existmap.clear();
            for (BPAResource resource : list) {
                existmap.put(resource.getResourceid(), resource);
            }
        }
    }

    public void initEnvironmentVar() {
        List<BPAEnvironmentVar> envVarlist = enviornmentVarService.findAll();
        Map<String, BPAEnvironmentVar> envVarMap = new HashMap<>();
        for (BPAEnvironmentVar bpaEnvironmentVar : envVarlist) {
            envVarMap.put(bpaEnvironmentVar.getName(), bpaEnvironmentVar);
        }
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.BluePrismEnvVAR, envVarMap);
        cache.put(element);
    }

    public void initProcess() {
        List<BPAProcess> processList = ibpaProcessService.findByProcessType("P");
        Map<String, BPAProcess> processMap = new HashMap<>();
        for (BPAProcess process : processList) {
            processMap.put(process.getProcessid(), process);
        }
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.BluePrismProcessList, processMap);
        cache.put(element);
    }

    public List<ComputerData> getAllBotList() {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BotCacheKey);
        if (element == null || cache.isExpired(element)) {
            return null;
        }
        List<ComputerData> computerDataList = new ArrayList<>();
        Map<String, ComputerData> computerDataMap = (Map<String, ComputerData>) element.getObjectValue();
        for (ComputerData cd : computerDataMap.values()) {
            computerDataList.add(cd);
        }
        return computerDataList;
    }

    public void UpdateComputerData(ComputerData cd) {
        if (cd == null) return;
        String id = cd.getBotIP();
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BotCacheKey);
        if (element == null || cache.isExpired(element) || element.getObjectValue() == null) {
            System.out.println("重新初始化缓存");
            InitConnectBot();
        }
        ;
        try {
            Map<String, ComputerData> computerDataMap = (Map<String, ComputerData>) element.getObjectValue();
            computerDataMap.put(id, cd);
        } catch (Exception e) {
            System.out.println("抓取到异常");
            if (element == null) {
                System.out.println("element 为空");
            }
            if (element != null && element.getObjectValue() == null) {
                System.out.println("element.getObjectValue() 为空");
            }
        }
    }

    public void DeleteComputerData(String id) {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BotCacheKey);
        if (element == null || cache.isExpired(element) || element.getObjectValue() == null) return;
        Map<String, ComputerData> computerDataMap = (Map<String, ComputerData>) element.getObjectValue();
        computerDataMap.remove(id);

    }

    public List<BPAResource> getResourceList() {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.ResourcesCacheKey);
        if (element == null || cache.isExpired(element)) {
            initResources();
            element = cache.get(CacheKey.ResourcesCacheKey);
        }
        Map<String, BPAResource> map = (Map<String, BPAResource>) element.getObjectValue();
        List<BPAResource> list = new ArrayList<>();
        for (BPAResource resource : map.values()) {
            list.add(resource);
        }
        return list;
    }

    public Map<String, BPAEnvironmentVar> getBPEnvVars() {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BluePrismEnvVAR);
        if (element == null || cache.isExpired(element)) {
            initEnvironmentVar();
            element = cache.get(CacheKey.BluePrismEnvVAR);
        }
        return (Map<String, BPAEnvironmentVar>) element.getObjectValue();
    }

    public Map<String, BPAProcess> getProcessList() {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BluePrismProcessList);
        if (element == null || cache.isExpired(element)) {
            initEnvironmentVar();
            element = cache.get(CacheKey.BluePrismProcessList);
        }
        return (Map<String, BPAProcess>) element.getObjectValue();
    }
}
