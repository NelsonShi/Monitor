package com.fast.bpserver.service;

import com.fast.bpserver.base.IService;
import com.fast.bpserver.entity.BPAUser;

import java.util.List;

/**
 * Created by sjlor on 2019/10/29.
 */
public interface IBPAUser extends IService<BPAUser> {
   List<BPAUser> findAll();
   BPAUser findById(String Id);
}
