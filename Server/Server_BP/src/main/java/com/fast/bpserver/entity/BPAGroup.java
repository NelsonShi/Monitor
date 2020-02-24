package com.fast.bpserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by Nelson on 2020/1/13.
 */
@Entity
@Table(name = "BPAGroup")
public class BPAGroup {
    @Id
    private String id;
    private Integer treeid;
    private String name;
    private boolean isrestricted;

    @Transient
    private List<BPAProcess> processList;

    public List<BPAProcess> getProcessList() {
        return processList;
    }

    public void setProcessList(List<BPAProcess> processList) {
        this.processList = processList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTreeid() {
        return treeid;
    }

    public void setTreeid(Integer treeid) {
        this.treeid = treeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsrestricted() {
        return isrestricted;
    }

    public void setIsrestricted(boolean isrestricted) {
        this.isrestricted = isrestricted;
    }
}
