package com.fast.bpserver.entity.vo;

/**
 * Created by Nelson on 2019/10/31.
 * resource 封装类 将BPAResource 的相关字段转换为前段显示信息
 */
public class BPAResourceVo {
    private String resourceid;
    private String name;
    // 0:pending 1:running 2:Terminated 3:Stopped 4: completed 5:debugging 6:Archived 7: Stopping
    private Integer processStatus;
    private String processName;
    private String timeSlot;
    private String lastupdated;
    private Integer AttributeID;
    private String FQDN;
    private String userName;
    private Integer statusid;
    private String DisplayStatus;
    private String botIp;
    private Integer isNettyConnected;
    //当前resource 是否可用
    private boolean available;
    //当前resource 是否被选择
    private boolean checked;

    public BPAResourceVo(){
        this.checked=false;
    }

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

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
