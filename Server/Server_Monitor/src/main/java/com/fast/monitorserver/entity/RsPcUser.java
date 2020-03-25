package com.fast.monitorserver.entity;

import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(name="generator", strategy = "native")
    private Integer id;
    private String userId;
    private String loginName;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
