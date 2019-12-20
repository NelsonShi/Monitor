package com.fast.bpserver.entity.postEntity;

import java.util.Date;

/**
 * Created by Nelson on 2019/12/17.
 */
public class ScheduleVoParams {
    private Date startTime;
    private Date endTime;
    private Integer requestTimeZone;
    private Date dateNow;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRequestTimeZone() {
        return requestTimeZone;
    }

    public void setRequestTimeZone(Integer requestTimeZone) {
        this.requestTimeZone = requestTimeZone;
    }

    public Date getDateNow() {
        return dateNow;
    }

    public void setDateNow(Date dateNow) {
        this.dateNow = dateNow;
    }
}
