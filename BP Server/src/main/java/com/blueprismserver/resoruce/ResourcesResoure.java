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



    @RequestMapping("/errorProcessVos")
    public List<BPAProcessVo> finderrorProcessVos(){
        Date dateNow=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateNow);
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date  querydate=calendar.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<BPASessionLogs> list= ibpaSessionService.findErrorSessionAndLogs(sdf.format(querydate));
        List<BPAProcessVo> voList=processService.GenerateUnCompetedProcessVos(list,cacheUtil.getProcessList(),querydate,dateNow);
        for (BPAProcessVo vo:voList){
            vo.InitMessage();
            vo.setLastTimeStr(vo.getLastTime()==null?"":sdf.format(vo.getLastTime()));
        }
        return voList;
    }

}
