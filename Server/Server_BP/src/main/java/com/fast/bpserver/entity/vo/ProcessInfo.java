package com.fast.bpserver.entity.vo;

/**
 * Created by Nelosn on 2019/10/25.
 * process(计算机进程) 信息对象
 * 主要记录process 的运行状态
 */
public class ProcessInfo {
    public String ProcessName;
    public Integer ProcessRunningStatus;
    public String ProcessId;
    public String WindowTitle;

    public String getProcessName() {
        return ProcessName;
    }

    public void setProcessName(String processName) {
        ProcessName = processName;
    }


    public Integer getProcessRunningStatus() {
        return ProcessRunningStatus;
    }

    public void setProcessRunningStatus(Integer processRunningStatus) {
        ProcessRunningStatus = processRunningStatus;
    }

    public String getProcessId() {
        return ProcessId;
    }

    public void setProcessId(String processId) {
        ProcessId = processId;
    }

    public String getWindowTitle() {
        return WindowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        WindowTitle = windowTitle;
    }
}
