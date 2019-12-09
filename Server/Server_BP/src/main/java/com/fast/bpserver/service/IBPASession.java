package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.entity.BPAResource;
import com.fast.bpserver.entity.BPASession;
import com.fast.bpserver.entity.BPAUser;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;

import java.util.List;

/**
 * Created by Neslon on 2019/10/29.
 */
public interface IBPASession extends IService<BPASession> {
    void RunProcess(BPAUser bpaUser, BPAResource bpaResource, BPAProcess bpaProcess);
    List<BPASession> recentlySession();
    List<BPASessionLogs> findErrorSessionAndLogs(String date);
}
