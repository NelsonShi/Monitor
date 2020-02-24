package com.fast.monitorserver.entity.vo;

import java.util.List;

/**
 * Created by Nelson on 2020/1/20.
 */
public class PendingProcessPara {
    private String userId;
    private String userName;
    private String processId;
    private List<String> resourceIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
