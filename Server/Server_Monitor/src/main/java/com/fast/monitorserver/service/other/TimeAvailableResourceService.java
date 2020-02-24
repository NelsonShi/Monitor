package com.fast.monitorserver.service.other;

import com.fast.bpserver.entity.vo.ScheduleTimeSlot;
import com.fast.bpserver.entity.vo.ScheduleVo;
import com.fast.monitorserver.entity.vo.TimeAvailableResourceVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson on 2020/1/17.
 */
@Service
public class TimeAvailableResourceService {
    public List<TimeAvailableResourceVo> GenerateRs(List<ScheduleVo> vos,Integer timeSpan){
         List<TimeAvailableResourceVo> resourceVos=new ArrayList<>();
         for (ScheduleVo vo:vos){
             if(vo.getTimeSlots()==null||vo.getTimeSlots().size()<=0){
                 TimeAvailableResourceVo timeAvailableResourceVo=new TimeAvailableResourceVo();
                 timeAvailableResourceVo.setResourceid(vo.getResourceid());
                 timeAvailableResourceVo.setAvailable(1);
                 timeAvailableResourceVo.setNextRunTime("60");
                 resourceVos.add(timeAvailableResourceVo);
             }else {
                 resourceVos.add(createTARV(vo,timeSpan));
             }
         }
         return resourceVos;
    }

    private TimeAvailableResourceVo createTARV(ScheduleVo scheduleVo,Integer timeSpan){
         ScheduleTimeSlot timeSlot=scheduleVo.getTimeSlots().get(0);
         TimeAvailableResourceVo timeAvailableResourceVo=new TimeAvailableResourceVo();
         timeAvailableResourceVo.setResourceid(scheduleVo.getResourceid());
         if(timeSlot.getSatrtMinutes()==0){
             timeAvailableResourceVo.setAvailable(-1);
             timeAvailableResourceVo.setNextRunTime("running");
         }else
         if(timeSlot.getSatrtMinutes()>timeSpan){
             timeAvailableResourceVo.setAvailable(1);
             timeAvailableResourceVo.setNextRunTime(timeSlot.getSatrtMinutes().toString());
         }else {
             timeAvailableResourceVo.setAvailable(0);
             timeAvailableResourceVo.setNextRunTime(timeSlot.getSatrtMinutes().toString());
         }
         return timeAvailableResourceVo;
    }
}
