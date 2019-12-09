package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAScheduleTrigger;

import java.util.List;

/**
 * Created by Nelson on 2019/11/11.
 */
public interface IBPAScheduleTrigger extends IService<BPAScheduleTrigger> {
    List<BPAScheduleTrigger> findAll();
}
