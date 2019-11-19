package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.BPAEnvironmentVar;

import java.util.List;

/**
 * Created by sjlor on 2019/11/11.
 */
public interface IBPAEnviornmentVar extends IService<BPAEnvironmentVar> {
    List<BPAEnvironmentVar> findAll();
}
