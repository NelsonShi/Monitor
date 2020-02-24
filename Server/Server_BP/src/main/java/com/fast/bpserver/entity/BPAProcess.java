package com.fast.bpserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nelson on 2019/10/23.
 */
@Entity
@Table(name = "BPAProcess")
public class BPAProcess {
    @Id
    private String processid;
    @Column(name="processtype")
    private String processType;
    private String name;
    private String description;
    private String version;
    private Date createdate;
    private String createdby;
    private Date lastmodifieddate;
    private String lastmodifiedby;
    private Integer AttributeID;
    private byte[] compressedxml;
    @Transient
    private String processxml;
    private String wspublishname;
    private Integer runmode;
    private Boolean sharedObject;
    private Boolean forceLiteralForm;
    private Boolean useLegacyNamespace;
    @Transient
    private String runTimeStr;
    @Transient
    private Integer runTimeMins;

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getLastmodifieddate() {
        return lastmodifieddate;
    }

    public void setLastmodifieddate(Date lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public Integer getAttributeID() {
        return AttributeID;
    }

    public void setAttributeID(Integer attributeID) {
        AttributeID = attributeID;
    }

    public String getProcessxml() {
        return processxml;
    }

    public void setProcessxml(String processxml) {
        this.processxml = processxml;
    }

    public String getWspublishname() {
        return wspublishname;
    }

    public void setWspublishname(String wspublishname) {
        this.wspublishname = wspublishname;
    }

    public Integer getRunmode() {
        return runmode;
    }

    public void setRunmode(Integer runmode) {
        this.runmode = runmode;
    }


    public byte[] getCompressedxml() {
        return compressedxml;
    }

    public void setCompressedxml(byte[] compressedxml) {
        this.compressedxml = compressedxml;
    }

    public Boolean getShareObject() {
        return sharedObject;
    }

    public void setShareObject(Boolean shareObject) {
        this.sharedObject = shareObject;
    }

    public Boolean getForceLiteralForm() {
        return forceLiteralForm;
    }

    public void setForceLiteralForm(Boolean forceLiteralForm) {
        this.forceLiteralForm = forceLiteralForm;
    }

    public Boolean getUseLegacyNamespace() {
        return useLegacyNamespace;
    }

    public void setUseLegacyNamespace(Boolean useLegacyNamespace) {
        this.useLegacyNamespace = useLegacyNamespace;
    }

    public String getRunTimeStr() {
        return runTimeStr;
    }

    public void setRunTimeStr(String runTimeStr) {
        this.runTimeStr = runTimeStr;
    }

    public Integer getRunTimeMins() {
        return runTimeMins;
    }

    public void setRunTimeMins(Integer runTimeMins) {
        this.runTimeMins = runTimeMins;
    }
}
