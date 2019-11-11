package com.blueprismserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by sjlor on 2019/11/11.
 */
@Entity
@Table(name = "BPATaskSession")
public class BPATaskSession {
    @Id
    private Integer id;
    private Integer taskid;
    private String processid;
    private boolean failonerror;
    private String processparams;
    private String resourcename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public boolean isFailonerror() {
        return failonerror;
    }

    public void setFailonerror(boolean failonerror) {
        this.failonerror = failonerror;
    }

    public String getProcessparams() {
        return processparams;
    }

    public void setProcessparams(String processparams) {
        this.processparams = processparams;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }
}
