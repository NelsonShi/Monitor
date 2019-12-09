package com.blueprismserver.utils;

import com.blueprismserver.entity.BPAEnvironmentVar;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.entity.BPAResource;
import com.blueprismserver.entity.vo.ComputerData;
import com.blueprismserver.service.IBPAEnviornmentVar;
import com.blueprismserver.service.IBPAProcess;
import com.blueprismserver.service.IBPAResource;
import com.blueprismserver.sysConfig.CacheKey;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


      public void InitCache(){
          initResources();
          InitConnectBot();
          initEnvironmentVar();
          initProcess();
      }

    public void InitConnectBot(){
        Map<String,ComputerData> computerDataMap=new HashMap<>(1024);
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.BotCacheKey, computerDataMap);
        cache.put(element);
    }

    public void initResources() {
        List<BPAResource> resourceList = resourceService.findAll();
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.ResourcesCacheKey, resourceList);
        cache.put(element);
    }

    public void initEnvironmentVar() {
        List<BPAEnvironmentVar> envVarlist = enviornmentVarService.findAll();
        Map<String,BPAEnvironmentVar> envVarMap=new HashMap<>();
        for (BPAEnvironmentVar bpaEnvironmentVar:envVarlist){
            envVarMap.put(bpaEnvironmentVar.getName(),bpaEnvironmentVar);
        }
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.BluePrismEnvVAR, envVarMap);
        cache.put(element);
    }

    public void initProcess() {
       List<BPAProcess> processList=ibpaProcessService.findByProcessType("P");
       Map<String,BPAProcess> processMap=new HashMap<>();
       for(BPAProcess process:processList){
           processMap.put(process.getProcessid(),process);
       }
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = new Element(CacheKey.BluePrismProcessList, processMap);
        cache.put(element);
    }

    public List<ComputerData> getAllBotList(){
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BotCacheKey);
        if (element == null || cache.isExpired(element)) {
            return null;
        }
        List<ComputerData> computerDataList=new ArrayList<>();
        Map<String,ComputerData> computerDataMap=(Map<String, ComputerData>) element.getObjectValue();
        for (ComputerData cd:computerDataMap.values()){
            computerDataList.add(cd);
        }
        return computerDataList;
    }

    public void UpdateComputerData(ComputerData cd){
        if(cd==null)return;
        String id=cd.getBotIP();
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BotCacheKey);
        if(element==null||cache.isExpired(element)||element.getObjectValue()==null){
            System.out.println("重新初始化缓存");
            InitConnectBot();};
        try {
            Map<String,ComputerData> computerDataMap=(Map<String, ComputerData>) element.getObjectValue();
            computerDataMap.put(id,cd);
        }catch (Exception e){
            System.out.println("抓取到异常");
            if(element==null){
                System.out.println("element 为空");
            }
            if(element!=null&&element.getObjectValue()==null){
                System.out.println("element.getObjectValue() 为空");
            }
        }
    }

    public void DeleteComputerData(String id){
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BotCacheKey);
        if(element==null||cache.isExpired(element)||element.getObjectValue()==null)return;
        Map<String,ComputerData> computerDataMap=(Map<String, ComputerData>) element.getObjectValue();
        computerDataMap.remove(id);

    }

    public List<BPAResource> getResourceList() {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.ResourcesCacheKey);
        if (element == null || cache.isExpired(element)) {
            initResources();
            element = cache.get(CacheKey.ResourcesCacheKey);
        }
        return (List<BPAResource>) element.getObjectValue();
    }

    public Map<String,BPAEnvironmentVar> getBPEnvVars() {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BluePrismEnvVAR);
        if (element == null || cache.isExpired(element)) {
            initEnvironmentVar();
            element = cache.get(CacheKey.BluePrismEnvVAR);
        }
        return (Map<String, BPAEnvironmentVar>) element.getObjectValue();
    }

    public Map<String,BPAProcess> getProcessList() {
        Cache cache = SpringContextUtil.getBean(CacheKey.DicCaching, Cache.class);
        Element element = cache.get(CacheKey.BluePrismProcessList);
        if (element == null || cache.isExpired(element)) {
            initEnvironmentVar();
            element = cache.get(CacheKey.BluePrismProcessList);
        }
        return (Map<String, BPAProcess>) element.getObjectValue();
    }
}
