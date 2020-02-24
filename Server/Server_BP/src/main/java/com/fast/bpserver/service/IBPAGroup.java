package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAGroup;
import com.fast.bpserver.entity.BPAProcess;

import java.util.List;

/**
 * Created by Nelson on 2020/1/13.
 */
public interface IBPAGroup extends IService<BPAGroup>{
    List<BPAGroup> findProcessWithGroup();
}
