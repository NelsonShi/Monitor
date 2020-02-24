package com.fast.bpserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Nelson on 2020/1/9.
 */
@Entity
@Table(name = "BPAInternalAuth")
public class BPAInternalAuth {
    private String userId;
    @Id
    private String token;
    private Date expiry;
    private String roles;
    private Integer loggedInMode;
    private String processId;
    private boolean isWebService;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getLoggedInMode() {
        return loggedInMode;
    }

    public void setLoggedInMode(Integer loggedInMode) {
        this.loggedInMode = loggedInMode;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public boolean isWebService() {
        return isWebService;
    }

    public void setWebService(boolean webService) {
        isWebService = webService;
    }
}
