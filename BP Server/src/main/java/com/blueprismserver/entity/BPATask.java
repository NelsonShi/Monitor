package com.blueprismserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Nelson on 2019/11/11.
 */
@Entity
@Table(name = "BPATask")
public class BPATask {
    @Id
    private Integer id;
    private Integer scheduleid;
    private String name;
    private String description;
    private Integer onsuccess;
    private Integer onfailure;
    private boolean failfastonerror;
    private Integer delayafterend;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOnsuccess() {
        return onsuccess;
    }

    public void setOnsuccess(Integer onsuccess) {
        this.onsuccess = onsuccess;
    }

    public Integer getOnfailure() {
        return onfailure;
    }

    public void setOnfailure(Integer onfailure) {
        this.onfailure = onfailure;
    }

    public boolean isFailfastonerror() {
        return failfastonerror;
    }

    public void setFailfastonerror(boolean failfastonerror) {
        this.failfastonerror = failfastonerror;
    }

    public Integer getDelayafterend() {
        return delayafterend;
    }

    public void setDelayafterend(Integer delayafterend) {
        this.delayafterend = delayafterend;
    }
}
