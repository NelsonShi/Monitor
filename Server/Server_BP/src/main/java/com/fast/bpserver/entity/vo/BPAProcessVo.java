package com.fast.bpserver.entity.vo;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Nelson on 2019/11/25.
 * web process 界面端用于展示的Vo
 */
public class BPAProcessVo {
    private String processId;
    private String processName;
//    private String errorCode;
//    private String errorDescription;
    private Integer errorCount;
    private Integer undefinedCount;
    private Date lastTime;
    private String lastTimeStr;
    private String message;
    private boolean requestedStoped;
    private String requestedTime;
    private boolean terminated;
    private List<ErrorChartVo> errorChart;
    private List<ProcessErrorInfoVo> errorCodes;
    private List<ProcessErrorLogVo> logs;


    public BPAProcessVo(String processId, String processName, Integer errorCount, Integer undefinedCount) {
        this.processId = processId;
        this.processName = processName;
        this.errorCount = errorCount;
        this.undefinedCount = undefinedCount;
    }

    public List<ErrorChartVo> getErrorChart() {
        return errorChart;
    }

    public void setErrorChart(List<ErrorChartVo> errorChart) {
        this.errorChart = errorChart;
    }

    public List<ProcessErrorInfoVo> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<ProcessErrorInfoVo> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public List<ProcessErrorLogVo> getLogs() {
        return logs;
    }

    public void setLogs(List<ProcessErrorLogVo> logs) {
        this.logs = logs;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getUndefinedCount() {
        return undefinedCount;
    }

    public void setUndefinedCount(Integer undefinedCount) {
        this.undefinedCount = undefinedCount;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRequestedStoped() {
        return requestedStoped;
    }

    public void setRequestedStoped(boolean requestedStoped) {
        this.requestedStoped = requestedStoped;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public String getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(String requestedTime) {
        this.requestedTime = requestedTime;
    }

    public String getLastTimeStr() {
        return lastTimeStr;
    }

    public void setLastTimeStr(String lastTimeStr) {
        this.lastTimeStr = lastTimeStr;
    }

    public void InitMessage(){
        if(requestedStoped){
            message="request stop";
        }
        if(!StringUtils.isEmpty(requestedTime)){
           message= message==null?"":message;
           message+=":"+requestedTime;
        }
        if(terminated){
            message=message==null?"":message;
            message+="Terminated";
        }
    }
}
