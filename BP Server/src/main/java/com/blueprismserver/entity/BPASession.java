package com.blueprismserver.entity;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Nelson on 2019/10/29.
 */
@Entity
@Table(name = "BPASession")
public class BPASession {
    @Id
    private String sessionid;
    private Integer sessionnumber;
    private Date startdatetime;
    private Date enddatetime;
    private String processid ;
    private String starterresourceid;
    private String starteruserid;
    private String runningresourceid;
    private String runningosusername;
    private Integer statusid;
    private String startparamsxml;
    private String logginglevelsxml;
    private String sessionstatexml;
    private Integer queueid;
    private Date stoprequested;
    private Date stoprequestack;
    private Date lastupdated;
    private String laststage;
    private Integer warningthreshold;
    private Integer starttimezoneoffset;
    private Integer endtimezoneoffset;
    private Integer lastupdatedtimezoneoffset;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Integer getSessionnumber() {
        return sessionnumber;
    }

    public void setSessionnumber(Integer sessionnumber) {
        this.sessionnumber = sessionnumber;
    }

    public Date getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(Date startdatetime) {
        this.startdatetime = startdatetime;
    }

    public Date getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(Date enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public String getStarterresourceid() {
        return starterresourceid;
    }

    public void setStarterresourceid(String starterresourceid) {
        this.starterresourceid = starterresourceid;
    }

    public String getStarteruserid() {
        return starteruserid;
    }

    public void setStarteruserid(String starteruserid) {
        this.starteruserid = starteruserid;
    }

    public String getRunningresourceid() {
        return runningresourceid;
    }

    public void setRunningresourceid(String runningresourceid) {
        this.runningresourceid = runningresourceid;
    }

    public String getRunningosusername() {
        return runningosusername;
    }

    public void setRunningosusername(String runningosusername) {
        this.runningosusername = runningosusername;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getStartparamsxml() {
        return startparamsxml;
    }

    public void setStartparamsxml(String startparamsxml) {
        this.startparamsxml = startparamsxml;
    }

    public String getLogginglevelsxml() {
        return logginglevelsxml;
    }

    public void setLogginglevelsxml(String logginglevelsxml) {
        this.logginglevelsxml = logginglevelsxml;
    }

    public String getSessionstatexml() {
        return sessionstatexml;
    }

    public void setSessionstatexml(String sessionstatexml) {
        this.sessionstatexml = sessionstatexml;
    }

    public Integer getQueueid() {
        return queueid;
    }

    public void setQueueid(Integer queueid) {
        this.queueid = queueid;
    }

    public Date getStoprequested() {
        return stoprequested;
    }

    public void setStoprequested(Date stoprequested) {
        this.stoprequested = stoprequested;
    }

    public Date getStoprequestack() {
        return stoprequestack;
    }

    public void setStoprequestack(Date stoprequestack) {
        this.stoprequestack = stoprequestack;
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getLaststage() {
        return laststage;
    }

    public void setLaststage(String laststage) {
        this.laststage = laststage;
    }

    public Integer getWarningthreshold() {
        return warningthreshold;
    }

    public void setWarningthreshold(Integer warningthreshold) {
        this.warningthreshold = warningthreshold;
    }

    public Integer getStarttimezoneoffset() {
        return starttimezoneoffset;
    }

    public void setStarttimezoneoffset(Integer starttimezoneoffset) {
        this.starttimezoneoffset = starttimezoneoffset;
    }

    public Integer getEndtimezoneoffset() {
        return endtimezoneoffset;
    }

    public void setEndtimezoneoffset(Integer endtimezoneoffset) {
        this.endtimezoneoffset = endtimezoneoffset;
    }

    public Integer getLastupdatedtimezoneoffset() {
        return lastupdatedtimezoneoffset;
    }

    public void setLastupdatedtimezoneoffset(Integer lastupdatedtimezoneoffset) {
        this.lastupdatedtimezoneoffset = lastupdatedtimezoneoffset;
    }
}
