package com.fast.bpserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by sjlor on 2019/10/29.
 */
@Entity
@Table(name = "BPAUser")
public class BPAUser {
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "username")
    private String userName;
    private Date validfromdate;
    private Date validtodate;
    private Date passwordexpirydate;
    private String useremail;
    private Boolean isdeleted;
    private Boolean UseEditSummaries;
    private String preferredStatisticsInterval;
    private Boolean SaveToolStripPositions;
    private Integer PasswordDurationWeeks;
    private Integer AlertEventTypes;
    private Integer AlertNotificationTypes;
    private Integer LogViewerHiddenColumns;
    private String systemusername;
    private Integer loginattempts;
    private Date lastsignedin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getValidfromdate() {
        return validfromdate;
    }

    public void setValidfromdate(Date validfromdate) {
        this.validfromdate = validfromdate;
    }

    public Date getValidtodate() {
        return validtodate;
    }

    public void setValidtodate(Date validtodate) {
        this.validtodate = validtodate;
    }

    public Date getPasswordexpirydate() {
        return passwordexpirydate;
    }

    public void setPasswordexpirydate(Date passwordexpirydate) {
        this.passwordexpirydate = passwordexpirydate;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Boolean getUseEditSummaries() {
        return UseEditSummaries;
    }

    public void setUseEditSummaries(Boolean useEditSummaries) {
        UseEditSummaries = useEditSummaries;
    }

    public String getPreferredStatisticsInterval() {
        return preferredStatisticsInterval;
    }

    public void setPreferredStatisticsInterval(String preferredStatisticsInterval) {
        this.preferredStatisticsInterval = preferredStatisticsInterval;
    }

    public Boolean getSaveToolStripPositions() {
        return SaveToolStripPositions;
    }

    public void setSaveToolStripPositions(Boolean saveToolStripPositions) {
        SaveToolStripPositions = saveToolStripPositions;
    }

    public Integer getPasswordDurationWeeks() {
        return PasswordDurationWeeks;
    }

    public void setPasswordDurationWeeks(Integer passwordDurationWeeks) {
        PasswordDurationWeeks = passwordDurationWeeks;
    }

    public Integer getAlertEventTypes() {
        return AlertEventTypes;
    }

    public void setAlertEventTypes(Integer alertEventTypes) {
        AlertEventTypes = alertEventTypes;
    }

    public Integer getAlertNotificationTypes() {
        return AlertNotificationTypes;
    }

    public void setAlertNotificationTypes(Integer alertNotificationTypes) {
        AlertNotificationTypes = alertNotificationTypes;
    }

    public Integer getLogViewerHiddenColumns() {
        return LogViewerHiddenColumns;
    }

    public void setLogViewerHiddenColumns(Integer logViewerHiddenColumns) {
        LogViewerHiddenColumns = logViewerHiddenColumns;
    }

    public String getSystemusername() {
        return systemusername;
    }

    public void setSystemusername(String systemusername) {
        this.systemusername = systemusername;
    }

    public Integer getLoginattempts() {
        return loginattempts;
    }

    public void setLoginattempts(Integer loginattempts) {
        this.loginattempts = loginattempts;
    }

    public Date getLastsignedin() {
        return lastsignedin;
    }

    public void setLastsignedin(Date lastsignedin) {
        this.lastsignedin = lastsignedin;
    }
}
