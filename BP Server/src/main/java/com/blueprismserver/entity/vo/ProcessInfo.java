package com.blueprismserver.entity.vo;

/**
 * Created by Nelosn on 2019/10/25.
 * process(计算机进程) 信息对象
 * 主要记录process 的运行状态
 */
public class ProcessInfo {
    public String ProcessName;
    public String ProcessRunningStatus;
    public String ProcessId;

    public String getProcessName() {
        return ProcessName;
    }

    public void setProcessName(String processName) {
        ProcessName = processName;
    }

    public String getProcessRunningStatus() {
        return ProcessRunningStatus;
    }

    public void setProcessRunningStatus(String processRunningStatus) {
        ProcessRunningStatus = processRunningStatus;
    }

    public String getProcessId() {
        return ProcessId;
    }

    public void setProcessId(String processId) {
        ProcessId = processId;
    }
}
