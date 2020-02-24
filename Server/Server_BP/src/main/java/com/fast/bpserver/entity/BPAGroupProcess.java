package com.fast.bpserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Nelson on 2020/1/13.
 */
@Entity
@Table(name = "BPAGroupProcess")
public class BPAGroupProcess {
    private String groupid;
    @Id
    private String processid;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }
}
