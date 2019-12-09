package com.fast.bpserver.entity.vo;

/**
 * Created by sjlor on 2019/11/12.
 */
public class ScheduleTimeSlot {
    //距离查询指定时间段 开始时间的分钟数
   private Integer satrtMinutes;
    //距离查询指定时间段 开始时间的分钟数
   private Integer endMinutes;
   private String processName;
   private String timeSpan;
   private String width;
   private String marginLeft;
   private String trggerName;
   private Integer index;

    public Integer getSatrtMinutes() {
        return satrtMinutes;
    }

    public void setSatrtMinutes(Integer satrtMinutes) {
        this.satrtMinutes = satrtMinutes;
    }

    public Integer getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(Integer endMinutes) {
        this.endMinutes = endMinutes;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    public String getTrggerName() {
        return trggerName;
    }

    public void setTrggerName(String trggerName) {
        this.trggerName = trggerName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
