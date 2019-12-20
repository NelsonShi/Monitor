package com.fast.bpserver.entity.postEntity;

/**
 * Created by Nelson on 2019/12/11.
 */
public class BotCommand {
    private String ip;
    private String type;
    private String processId;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
