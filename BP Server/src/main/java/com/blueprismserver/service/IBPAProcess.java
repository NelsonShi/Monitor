package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.BPAProcess;

import java.util.List;

/**
 * Created by sjlor on 2019/10/24.
 */
public interface IBPAProcess extends IService<BPAProcess> {
    List<BPAProcess> findByProcessType(String type);
    List<BPAProcess> findAll();
}
