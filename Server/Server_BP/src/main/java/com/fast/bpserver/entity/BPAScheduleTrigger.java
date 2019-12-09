package com.fast.bpserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Nelson on 2019/11/11.
 */
@Entity
@Table(name = "BPAScheduleTrigger")
public class BPAScheduleTrigger {
    @Id
    private Integer id;
    private Integer scheduleid;
    private Integer priority;
    private Integer mode;
    private Integer unittype;
    private Integer period;
    private Date startdate;
    private Date enddate;
    private Integer startpoint;
    private Integer endpoint;
    private Integer dayset;
    private Integer calendarid;
    private Integer nthofmonth;
    private Integer missingdatepolicy;
    private boolean usertrigger;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getUnittype() {
        return unittype;
    }

    public void setUnittype(Integer unittype) {
        this.unittype = unittype;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(Integer startpoint) {
        this.startpoint = startpoint;
    }

    public Integer getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Integer endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getDayset() {
        return dayset;
    }

    public void setDayset(Integer dayset) {
        this.dayset = dayset;
    }

    public Integer getCalendarid() {
        return calendarid;
    }

    public void setCalendarid(Integer calendarid) {
        this.calendarid = calendarid;
    }

    public Integer getNthofmonth() {
        return nthofmonth;
    }

    public void setNthofmonth(Integer nthofmonth) {
        this.nthofmonth = nthofmonth;
    }

    public Integer getMissingdatepolicy() {
        return missingdatepolicy;
    }

    public void setMissingdatepolicy(Integer missingdatepolicy) {
        this.missingdatepolicy = missingdatepolicy;
    }

    public boolean isUsertrigger() {
        return usertrigger;
    }

    public void setUsertrigger(boolean usertrigger) {
        this.usertrigger = usertrigger;
    }
}
