package com.fast.monitorserver.entity.vo;

import java.util.Date;

/**
 * Created by Nelson on 2020/1/17.
 */
public class TimeAvailabePara {
   private Date timeStart;
   private Integer timeZone;
   private Integer timeSpan;

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(Integer timeSpan) {
        this.timeSpan = timeSpan;
    }
}
