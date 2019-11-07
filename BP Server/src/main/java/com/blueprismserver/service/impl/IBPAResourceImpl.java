package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPAResourceDao;
import com.blueprismserver.entity.BPAResource;
import com.blueprismserver.entity.BPAUser;
import com.blueprismserver.entity.vo.BPAResourceVo;
import com.blueprismserver.entity.vo.ComputerData;
import com.blueprismserver.service.IBPAResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sjlor on 2019/10/24.
 */
@Service
public class IBPAResourceImpl extends AbstractService<BPAResource> implements IBPAResource {
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Autowired
    private IBPAResourceDao bpaResourceDao;
    @Override
    public JpaRepository<BPAResource,String> getRepository() {
        return bpaResourceDao;
    }

    @Override
    public List<BPAResource> findAll(){
        return bpaResourceDao.findAll();
    }

    @Override
    public List<BPAResourceVo> GenrateListWithResourceAndUser(Map<String,ComputerData> computerDataMap, Map<String,BPAUser> userMap, List<BPAResource> resourceList){
        List<BPAResourceVo> bpaResourceVoList=new ArrayList<>();
        for (BPAResource resource:resourceList){
            BPAResourceVo vo=new BPAResourceVo();
            vo.setResourceid(resource.getResourceid());
            vo.setName(resource.getName());
            vo.setProcessesrunning(resource.getProcessesrunning());
            vo.setActionsrunning(resource.getActionsrunning());
            vo.setLastupdated(sdf.format(resource.getLastupdated()));
            vo.setAttributeID(resource.getAttributeID());
            vo.setFQDN(resource.getFQDN());
            vo.setUserName(resource.getUserID()==null?"":userMap.get(resource.getUserID())==null?"":userMap.get(resource.getUserID()).getUsername());
            vo.setStatusid(resource.getStatusid());
            vo.setDisplayStatus(resource.getDisplayStatus());
            if(computerDataMap==null||computerDataMap.isEmpty()){
                vo.setBotIp("Netty error:404");
                vo.setIsNettyConnected(0);
            }else {
                boolean hasNettyConnected=computerDataMap.get(vo.getName())!=null;
                vo.setBotIp(hasNettyConnected?computerDataMap.get(vo.getName()).getBotIP():"");
                vo.setIsNettyConnected(hasNettyConnected?1:0);
            }
            bpaResourceVoList.add(vo);
        }
        return bpaResourceVoList;
    }

}
