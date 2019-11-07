package com.blueprismserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by sjlor on 2019/10/24.
 */
@Entity
@Table(name = "BPAResource")
public class BPAResource {
    @Id
    private String resourceid;
    private String name;
    private Integer processesrunning;
    private Integer actionsrunning;
    private Integer unitsallocated;
    private Date lastupdated;
    private Integer AttributeID;
    private String pool;
    private String controller;
    private Integer diagnostics;
    private Boolean logtoeventlog;
    private String FQDN;
    private Boolean ssl;
    private String userID;
    private Integer statusid;
    private String DisplayStatus;

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

    public Integer getUnisallocated() {
        return unitsallocated;
    }

    public void setUnisallocated(Integer unitsallocated) {
        this.unitsallocated = unitsallocated;
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Integer getAttributeID() {
        return AttributeID;
    }

    public void setAttributeID(Integer attributeID) {
        AttributeID = attributeID;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public Integer getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(Integer diagnostics) {
        this.diagnostics = diagnostics;
    }

    public Boolean getLogtoeventlog() {
        return logtoeventlog;
    }

    public void setLogtoeventlog(Boolean logtoeventlog) {
        this.logtoeventlog = logtoeventlog;
    }

    public String getFQDN() {
        return FQDN;
    }

    public void setFQDN(String FQDN) {
        this.FQDN = FQDN;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
}
