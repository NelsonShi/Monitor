package com.fast.bpserver.entity.vo;

/**
 * Created by Nelson on 2019/12/3.
 * process Error 对象 用于 process detial chart 展示
 */
public class ErrorChartVo {
    private String hour;
    private Integer errorCount;
    private Integer timeSpan;
    private Integer sortNo;

    public ErrorChartVo(String hour, Integer errorCount, Integer timeSpan, Integer sortNo) {
        this.hour = hour;
        this.errorCount = errorCount;
        this.timeSpan = timeSpan;
        this.sortNo = sortNo;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(Integer timeSpan) {
        this.timeSpan = timeSpan;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}
