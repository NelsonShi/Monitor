package com.fast.bpserver.entity.vo;

/**
 * Created by Nelson on 2020/3/19.
 *
 * 控制session 结果返回值
 */
public class SessionControlResult {
    //0 超时 1 成功获取  2 异常 3其他原因过程未完成
    private Integer result;
    private String message;
    private String resourceName;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
