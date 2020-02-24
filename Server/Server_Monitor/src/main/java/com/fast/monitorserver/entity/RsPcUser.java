package com.fast.monitorserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Nelson on 2020/1/10.
 */
@Entity
@Table(name = "resource_pc_user")
public class RsPcUser {
    @Id
    @GeneratedValue(generator="generator")
    private Integer id;
    private String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
