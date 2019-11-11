package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.entity.BPAResource;
import com.blueprismserver.entity.BPASession;
import com.blueprismserver.entity.BPAUser;

import java.util.List;

/**
 * Created by sjlor on 2019/10/29.
 */
public interface IBPASession extends IService<BPASession> {
    void RunProcess(BPAUser bpaUser, BPAResource bpaResource, BPAProcess bpaProcess);
    List<BPASession> findRunningProcess();
    List<BPASession> recentlySession();
}
