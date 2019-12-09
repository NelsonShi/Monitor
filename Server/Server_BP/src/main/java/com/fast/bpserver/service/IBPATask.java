package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPATask;

import java.util.List;

/**
 * Created by sjlor on 2019/11/11.
 */
public interface IBPATask extends IService<BPATask> {
    List<BPATask> findAll();
}
