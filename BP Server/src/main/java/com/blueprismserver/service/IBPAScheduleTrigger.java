package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.BPAScheduleTrigger;

import java.util.List;

/**
 * Created by Nelson on 2019/11/11.
 */
public interface IBPAScheduleTrigger extends IService<BPAScheduleTrigger> {
    List<BPAScheduleTrigger> findAll();
}
