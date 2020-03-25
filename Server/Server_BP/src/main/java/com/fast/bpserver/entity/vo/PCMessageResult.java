package com.fast.bpserver.entity.vo;

/**
 * Created by Nelson on 2020/3/19.
 * 获取PC Socket 返回消息结果 载体
 */
public class PCMessageResult {
    //1 成功获取 0 超时 2 异常
    private Integer result;
    private String message;

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
}