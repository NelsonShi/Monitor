package com.fast.monitorserver.entity;

import javax.persistence.*;

/**
 * Created by Nelson on 2020/1/10.
 */
@Entity
@Table(name = "resource_pc_connect_command")
public class RsPcConnectCommand {
    @Id
    @GeneratedValue(generator="generator")
    private Integer id;
    @Column(name = "order_no")
    private Integer orderNo;
    @Column(name = "connected_commandStr")
    private String connectedCommandStr;
    private Integer type;
    @Column(name = "end_char")
    private String endChar;
    private String group;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getConnectedCommandStr() {
        return connectedCommandStr;
    }

    public void setConnectedCommandStr(String connectedCommandStr) {
        this.connectedCommandStr = connectedCommandStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEndChar() {
        return endChar;
    }

    public void setEndChar(String endChar) {
        this.endChar = endChar;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
