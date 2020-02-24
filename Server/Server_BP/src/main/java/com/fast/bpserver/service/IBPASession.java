package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.entity.BPAResource;
import com.fast.bpserver.entity.BPASession;
import com.fast.bpserver.entity.BPAUser;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.postEntity.SessionParams;
import io.netty.channel.Channel;

import java.util.List;

/**
 * Created by Neslon on 2019/10/29.
 */
public interface IBPASession extends IService<BPASession> {
    void PendingProcess(BPAUser bpaUser,BPAProcess bpaProcess, Integer timeSpan, Channel channel);
    List<BPASession> recentlySession();
    List<BPASessionLogs> findErrorSessionAndLogs(String date);
    BPASession findPendingProcess(String processId);
    BPASession findPendingSession(String resourceId);
    List<BPASession> findAllPendingSessions();
    List<BPASession> findSessionWithParams(SessionParams params);
}
