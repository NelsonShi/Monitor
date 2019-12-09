package com.blueprismserver.entity.vo;

/**
 * Created by Nelson on 2019/12/3.
 * Process Error Logs
 */
public class ProcessErrorLogVo {
    private long id;
    private String time;
    private String stageName;
    private String logResult;

    public ProcessErrorLogVo(long id,String time, String stageName, String logResult) {
        this.id=id;
        this.time = time;
        this.stageName = stageName;
        this.logResult = logResult;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getLogResult() {
        return logResult;
    }

    public void setLogResult(String logResult) {
        this.logResult = logResult;
    }
}
