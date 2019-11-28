package com.blueprismserver.service;

import com.blueprismserver.base.IService;
import com.blueprismserver.entity.BPASessionLogNonUnicode;

import java.util.Date;
import java.util.List;

/**
 * Created by sjlor on 2019/11/26.
 */
public interface IBPASessionLogNonUnicode  extends IService<BPASessionLogNonUnicode> {
    List<BPASessionLogNonUnicode> findErrorLogs(Date date,String errorFlag);
}
