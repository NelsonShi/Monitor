package com.fast.bpserver.entity.vo;

import java.util.List;

/**
 * Created by Nelson on 2019/10/25.
 * bot 信息对象 记录了bot的电脑运行状态
 */
public class ComputerData {
    public String BotName;
    public String BotIP;
    public Double CPUUseRate;
    public String Resolution;
    public Double RAMUseRate;
    public String CPUCount;
    public List<ProcessInfo> processList;

    public String getBotName() {
        return BotName;
    }

    public void setBotName(String botName) {
        BotName = botName;
    }

    public String getBotIP() {
        return BotIP;
    }

    public void setBotIP(String botIP) {
        BotIP = botIP;
    }

    public String getResolution() {
        return Resolution;
    }

    public void setResolution(String resolution) {
        Resolution = resolution;
    }

    public Double getCPUUseRate() {
        return CPUUseRate;
    }

    public void setCPUUseRate(Double CPUUseRate) {
        this.CPUUseRate = CPUUseRate;
    }

    public Double getRAMUseRate() {
        return RAMUseRate;
    }

    public void setRAMUseRate(Double RAMUseRate) {
        this.RAMUseRate = RAMUseRate;
    }

    public String getCPUCount() {
        return CPUCount;
    }

    public void setCPUCount(String CPUCount) {
        this.CPUCount = CPUCount;
    }

    public List<ProcessInfo> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessInfo> processList) {
        this.processList = processList;
    }
}
