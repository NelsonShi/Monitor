package com.blueprismserver.resoruce;

import com.blueprismserver.base.BaseResource;
import com.blueprismserver.entity.BPAProcess;
import com.blueprismserver.entity.BPASession;
import com.blueprismserver.entity.BPASessionLogNonUnicode;
import com.blueprismserver.entity.QueryVo.BPASessionLogs;
import com.blueprismserver.entity.vo.BPAProcessVo;
import com.blueprismserver.service.IBPAProcess;
import com.blueprismserver.service.IBPASession;
import com.blueprismserver.service.IBPASessionLogNonUnicode;
import com.blueprismserver.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nelson on 2019/11/11.
 */
@RestController
@RequestMapping("/resource")
public class ResourcesResoure extends BaseResource {
    @Autowired
    private IBPASession ibpaSessionService;
    @Autowired
    private IBPAProcess processService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private IBPASessionLogNonUnicode ibpaSessionLogNonUnicodeService;


    @RequestMapping("/errorProcessVos")
    public List<BPAProcessVo> finderrorProcessVos(){
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-6);
        date=calendar.getTime();
        List<BPASessionLogs> list= ibpaSessionService.findErrorSessionAndLogs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        List<BPAProcessVo> voList=processService.GenerateUnCompetedProcessVos(list,cacheUtil.getProcessList());
        for (BPAProcessVo vo:voList){
            vo.InitMessage();
        }
        return voList;
    }

}
