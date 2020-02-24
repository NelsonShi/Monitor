package com.fast.bpserver.entity.vo;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sjlor on 2019/11/12.
 */
public class ScheduleVo {
    private String resourceid;
    private String resourcename;
    //查询时间段 单位分钟
    private Integer timeSpan;
    private List<ScheduleTimeSlot> timeSlots;
    private Integer warningStatus;

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public List<ScheduleTimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<ScheduleTimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public Integer getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(Integer warningStatus) {
        this.warningStatus = warningStatus;
    }

    public Integer getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(Integer timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void SortTimeSlots(){
        if(timeSlots==null||timeSlots.size()<=0)return;
       timeSlots.sort(new Comparator<ScheduleTimeSlot>() {
           @Override
           public int compare(ScheduleTimeSlot o1, ScheduleTimeSlot o2) {
             return  o1.getSatrtMinutes()<o2.getSatrtMinutes()?-1:1;
           }
       });
   }

   public void SetSlotMargins(){
        if(timeSpan<=0||timeSlots==null)return;
        DecimalFormat sd=new DecimalFormat("0.00");
        for(int i=0;i<timeSlots.size();i++){
           Integer lastEndTime;
           if(i==0){
               lastEndTime=0;
           }else {
               lastEndTime=timeSlots.get(i-1).getEndMinutes();
           }
           double marginLeft=(timeSlots.get(i).getSatrtMinutes()-lastEndTime)*100.0/timeSpan;
           if(marginLeft<0){
               String width=timeSlots.get(i).getWidth();
               Double widthd=Double.parseDouble(width);
               Double newWidth=widthd+marginLeft;
               if(newWidth<=0){
                   newWidth=0.0;
               }
               timeSlots.get(i).setWidth(newWidth.toString());
               timeSlots.get(i).setMarginLeft("0");
           }else {
               timeSlots.get(i).setMarginLeft(sd.format(marginLeft));
           }

           timeSlots.get(i).setIndex(i);
        }
   }
}
