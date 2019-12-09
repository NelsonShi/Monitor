package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAEnvironmentVar;

import java.util.List;

/**
 * Created by sjlor on 2019/11/11.
 */
public interface IBPAEnviornmentVar extends IService<BPAEnvironmentVar> {
    List<BPAEnvironmentVar> findAll();
}
