package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.BPASession;
import com.blueprismserver.entity.BPATaskSession;

import java.util.List;

/**
 * Created by sjlor on 2019/11/11.
 */
public interface IBPATaskSession extends IService<BPATaskSession> {
    List<BPATaskSession> findAll();
}
