package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAProcess;
import com.fast.bpserver.entity.BPAResource;
import com.fast.bpserver.entity.BPASession;
import com.fast.bpserver.entity.BPAUser;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.postEntity.SessionParams;
import com.fast.bpserver.entity.vo.SessionControlResult;
import io.netty.channel.Channel;

import java.util.List;

/**
 * Created by Neslon on 2019/10/29.
 */
public interface IBPASession extends IService<BPASession> {
    SessionControlResult PendingProcess(BPAUser bpaUser, BPAProcess bpaProcess, Integer timeSpan, Channel channel, String ip);
    SessionControlResult DeleteSession(BPAUser bpaUser,String sessionId,Channel channel,String ip);
    SessionControlResult StopSession(BPAUser bpaUser,String sessionId,Channel channel,String resourceName,String ip);
    SessionControlResult StartSession(Channel channel,String ip,String resourceId);
    List<BPASession> recentlySession();
    List<BPASessionLogs> findErrorSessionAndLogs(String date);
    BPASession findPendingProcess(String processId);
    BPASession findPendingSession(String resourceId);
    List<BPASession> findAllPendingSessions();
    List<BPASession> findSessionWithParams(SessionParams params);
}
