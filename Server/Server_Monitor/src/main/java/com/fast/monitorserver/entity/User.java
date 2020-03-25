package com.fast.monitorserver.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Nelson on 2019/12/6.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(generator="generator")
    @GenericGenerator(name="generator", strategy = "native")
    private long Id;
    private String name;
    private Integer gender;
    @Column(name = "login_name")
    private String loginName;
    private String password;
    @Column(name = "department_id")
    private Integer departmentId;
    private String bpUserId;
    //状态标识， 0  一切正常
    private Integer status;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getBpUserId() {
        return bpUserId;
    }

    public void setBpUserId(String bpUserId) {
        this.bpUserId = bpUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
