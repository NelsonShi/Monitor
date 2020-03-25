package com.fast.monitorserver.entity.vo;

/**
 * Created by Nelson on 2020/1/21.
 */
public class ControlSessionPara {
    private String resourceId;
    private String sessionId;
    private String userId;

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
