package com.fast.monitorserver.entity.vo;

/**
 * Created by Nelson on 2020/1/20.
 */
public class PendingProcessRes {
    private Integer status;
    private String description;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
