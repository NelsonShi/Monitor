package com.fast.bpserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by sjlor on 2019/11/11.
 */
@Entity
@Table(name = "BPASchedule")
public class BPASchedule {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Integer initialtaskid;
    @Column(name="retired")
    private boolean retired;
    private  Integer versionno;
    private String deletedname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getInitialtaskid() {
        return initialtaskid;
    }

    public void setInitialtaskid(Integer initialtaskid) {
        this.initialtaskid = initialtaskid;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public Integer getVersionno() {
        return versionno;
    }

    public void setVersionno(Integer versionno) {
        this.versionno = versionno;
    }

    public String getDeletedname() {
        return deletedname;
    }

    public void setDeletedname(String deletedname) {
        this.deletedname = deletedname;
    }
}
