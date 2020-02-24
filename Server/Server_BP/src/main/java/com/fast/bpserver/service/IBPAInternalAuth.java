package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAInternalAuth;

/**
 * Created by Nelson on 2020/1/10.
 */
public interface IBPAInternalAuth extends IService<BPAInternalAuth>{
    BPAInternalAuth GenrateInternalAuth(String userId,String processId);
}
