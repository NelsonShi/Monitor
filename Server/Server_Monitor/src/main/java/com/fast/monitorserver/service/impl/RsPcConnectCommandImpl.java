package com.fast.monitorserver.service.impl;

import com.fast.bpserver.base.AbstractService;
import com.fast.monitorserver.dao.IRsPcConnectCommandDao;
import com.fast.monitorserver.entity.RsPcConnectCommand;
import com.fast.monitorserver.service.IRsPcConnectCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Nelson on 2020/1/10.
 */
@Service
public class RsPcConnectCommandImpl extends AbstractService<RsPcConnectCommand> implements IRsPcConnectCommand {
    @Autowired
    private IRsPcConnectCommandDao rsPcConnectCommandDao;
    @Override
    public JpaRepository<RsPcConnectCommand,String> getRepository(){return  rsPcConnectCommandDao;}
}
