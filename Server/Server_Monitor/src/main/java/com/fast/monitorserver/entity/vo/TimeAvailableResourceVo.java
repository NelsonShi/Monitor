package com.fast.monitorserver.entity.vo;

/**
 * Created by Nelson on 2020/1/17.
 */
public class TimeAvailableResourceVo {
    private String resourceid;
    private String nextRunTime;
    private Integer available;

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(String nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
