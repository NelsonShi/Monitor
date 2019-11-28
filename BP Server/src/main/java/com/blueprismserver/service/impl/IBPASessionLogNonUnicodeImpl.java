package com.blueprismserver.service.impl;

import com.blueprismserver.base.AbstractService;
import com.blueprismserver.dao.IBPASessionLogNonUnicodeDao;
import com.blueprismserver.entity.BPASessionLogNonUnicode;
import com.blueprismserver.service.IBPASessionLogNonUnicode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Nelson on 2019/11/26.
 */
@Service
public class IBPASessionLogNonUnicodeImpl extends AbstractService<BPASessionLogNonUnicode> implements IBPASessionLogNonUnicode{
    @Autowired
    private IBPASessionLogNonUnicodeDao ibpaSessionLogNonUnicodeDao;
    @Override
    public JpaRepository<BPASessionLogNonUnicode,String> getRepository(){return ibpaSessionLogNonUnicodeDao;}

    /**
     * 查找bp日志
     * @param date 指定时间，查找该时间以后的数据
     * @param errorFlag 指定标记，查找带有此标记
     * @return
     */
    @Override
    public List<BPASessionLogNonUnicode> findErrorLogs(Date date, String errorFlag) {
        return ibpaSessionLogNonUnicodeDao.findByStagenameContainsAndStartdatetimeAfterOrResultContainsAndStartdatetimeAfter(errorFlag,date,"ERROR:",date);
    }
}
