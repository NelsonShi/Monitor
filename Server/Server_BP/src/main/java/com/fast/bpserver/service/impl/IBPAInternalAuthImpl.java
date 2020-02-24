package com.fast.bpserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.bpserver.dao.IBPAInternalAuthDao;
import com.fast.bpserver.entity.BPAInternalAuth;
import com.fast.bpserver.service.IBPAInternalAuth;
import com.fast.bpserver.utils.TimeZoneUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by Nelson on 2020/1/10.
 */
@Service
public class IBPAInternalAuthImpl extends AbstractService<BPAInternalAuth> implements IBPAInternalAuth {
    @Autowired
    private IBPAInternalAuthDao ibpaInternalAuthDao;
    @Value("${server.props.DBtimeZone}")
    private Integer DBTimeZone;
    @Override
    public JpaRepository<BPAInternalAuth, String> getRepository() {
        return ibpaInternalAuthDao;
    }

    @Override
    public BPAInternalAuth GenrateInternalAuth(String userId,String processId) {
        int sysTimeZone=  TimeZone.getDefault().getOffset(new Date().getTime())/(1000*60*60);
        BPAInternalAuth bpaInternalAuth=new BPAInternalAuth();
        bpaInternalAuth.setUserId(userId);
        if(!StringUtils.isEmpty(processId)){
            bpaInternalAuth.setProcessId(processId);
        }
        bpaInternalAuth.setToken(UUID.randomUUID().toString());
        bpaInternalAuth.setExpiry(TimeZoneUtil.formateDateToZone(new Date(),(sysTimeZone-DBTimeZone-1)));
        bpaInternalAuth.setLoggedInMode(1);
        bpaInternalAuth.setRoles("1");
        bpaInternalAuth.setWebService(false);
        return ibpaInternalAuthDao.save(bpaInternalAuth);
    }
}
