package com.blueprismserver.entity.vo;

import java.util.Date;

/**
 * Created by Nelson on 2019/10/31.
 * resource 封装类 将BPAResource 的相关字段转换为前段显示信息
 */
public class BPAResourceVo {
    private String resourceid;
    private String name;
    private Integer processesrunning;
    private Integer actionsrunning;
    private String lastupdated;
    private Integer AttributeID;
    private String FQDN;
    private String userName;
    private Integer statusid;
    private String DisplayStatus;
    private String botIp;
    private Integer isNettyConnected;

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProcessesrunning() {
        return processesrunning;
    }

    public void setProcessesrunning(Integer processesrunning) {
        this.processesrunning = processesrunning;
    }

    public Integer getActionsrunning() {
        return actionsrunning;
    }

    public void setActionsrunning(Integer actionsrunning) {
        this.actionsrunning = actionsrunning;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Integer getAttributeID() {
        return AttributeID;
    }

    public void setAttributeID(Integer attributeID) {
        AttributeID = attributeID;
    }

    public String getFQDN() {
        return FQDN;
    }

    public void setFQDN(String FQDN) {
        this.FQDN = FQDN;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getDisplayStatus() {
        return DisplayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        DisplayStatus = displayStatus;
    }

    public String getBotIp() {
        return botIp;
    }

    public void setBotIp(String botIp) {
        this.botIp = botIp;
    }

    public Integer getIsNettyConnected() {
        return isNettyConnected;
    }

    public void setIsNettyConnected(Integer isNettyConnected) {
        this.isNettyConnected = isNettyConnected;
    }
}
