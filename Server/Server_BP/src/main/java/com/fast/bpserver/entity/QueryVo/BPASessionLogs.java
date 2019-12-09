package com.fast.bpserver.entity.QueryVo;

import java.util.Date;

/**
 * Created by Nelson on 2019/11/25.
 * 用于存储源生SQL结果
 * 存储Sessin 和 session log
 */
public class BPASessionLogs {
    private String sessionid;
    private Date startdatetime;
    private Date enddatetime;
    private String processid ;
    private String starterresourceid;
    private Integer statusid;
    private Date stoprequested;
    private Date lastupdated;
    private String laststage;
    private String stagename;
    private String pagename;
    private String objectname;
    private String actionname;
    private String result;

    public BPASessionLogs(){

    }

    public BPASessionLogs(String sessionid, Date startdatetime, Date enddatetime, String processid, String starterresourceid, Integer statusid, Date stoprequested, Date lastupdated, String laststage, String stagename, String pagename, String objectname, String actionname, String result) {
        this.sessionid = sessionid;
        this.startdatetime = startdatetime;
        this.enddatetime = enddatetime;
        this.processid = processid;
        this.starterresourceid = starterresourceid;
        this.statusid = statusid;
        this.stoprequested = stoprequested;
        this.lastupdated = lastupdated;
        this.laststage = laststage;
        this.stagename = stagename;
        this.pagename = pagename;
        this.objectname = objectname;
        this.actionname = actionname;
        this.result = result;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
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

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public Date getStoprequested() {
        return stoprequested;
    }

    public void setStoprequested(Date stoprequested) {
        this.stoprequested = stoprequested;
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

    public String getStagename() {
        return stagename;
    }

    public void setStagename(String stagename) {
        this.stagename = stagename;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getObjectname() {
        return objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
