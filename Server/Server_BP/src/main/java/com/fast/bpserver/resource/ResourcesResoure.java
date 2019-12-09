package com.fast.bpserver.resource;

import com.fast.bpserver.base.BaseResource;
import com.fast.bpserver.entity.QueryVo.BPASessionLogs;
import com.fast.bpserver.entity.vo.BPAProcessVo;
import com.fast.bpserver.service.IBPAProcess;
import com.fast.bpserver.service.IBPASession;
import com.fast.bpserver.utils.CacheUtil;
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
        calendar.add(Calendar.DAY_OF_MONTH,-1);
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
