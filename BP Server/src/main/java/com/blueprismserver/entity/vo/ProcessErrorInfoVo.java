package com.blueprismserver.entity.vo;

/**
 * Created by Nelson on 2019/12/2.
 * process error info
 */
public class ProcessErrorInfoVo {
    private String errorType;
    private Integer errorCount;
    private String errorDescription;

    public ProcessErrorInfoVo(String errorType, Integer errorCount, String errorDescription) {
        this.errorType = errorType;
        this.errorCount = errorCount;
        this.errorDescription = errorDescription;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }


    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

}
