package com.blueprismserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Nelson on 2019/11/26.
 */
@Entity
@Table(name = "BPASessionLog_NonUnicode")
public class BPASessionLogNonUnicode {
    @Id
    private long logid;
    private Integer sessionnumber;
    private String stageid;
    private String stagename;
    private Integer stagetype;
    private String processname;
    private String pagename;
    private String objectname;
    private String actionname;
    private String result;
    private Integer resulttype;
    private Date startdatetime;
    private Date enddatetime;
    private String attributexml;
    private Integer automateworkingset;
    private String targetappname;
    private Integer targetappworkingset;
    private Integer starttimezoneoffset;
    private Integer endtimezoneoffset;

    public long getLogid() {
        return logid;
    }

    public void setLogid(long logid) {
        this.logid = logid;
    }

    public Integer getSessionnumber() {
        return sessionnumber;
    }

    public void setSessionnumber(Integer sessionnumber) {
        this.sessionnumber = sessionnumber;
    }

    public String getStageid() {
        return stageid;
    }

    public void setStageid(String stageid) {
        this.stageid = stageid;
    }

    public String getStagename() {
        return stagename;
    }

    public void setStagename(String stagename) {
        this.stagename = stagename;
    }

    public Integer getStagetype() {
        return stagetype;
    }

    public void setStagetype(Integer stagetype) {
        this.stagetype = stagetype;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
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

    public Integer getResulttype() {
        return resulttype;
    }

    public void setResulttype(Integer resulttype) {
        this.resulttype = resulttype;
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

    public String getAttributexml() {
        return attributexml;
    }

    public void setAttributexml(String attributexml) {
        this.attributexml = attributexml;
    }

    public Integer getAutomateworkingset() {
        return automateworkingset;
    }

    public void setAutomateworkingset(Integer automateworkingset) {
        this.automateworkingset = automateworkingset;
    }

    public String getTargetappname() {
        return targetappname;
    }

    public void setTargetappname(String targetappname) {
        this.targetappname = targetappname;
    }

    public Integer getTargetappworkingset() {
        return targetappworkingset;
    }

    public void setTargetappworkingset(Integer targetappworkingset) {
        this.targetappworkingset = targetappworkingset;
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
}
