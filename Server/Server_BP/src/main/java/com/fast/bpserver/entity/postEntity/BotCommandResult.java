package com.fast.bpserver.entity.postEntity;

/**
 * Created by Nelson on 2019/12/11.
 */
public class BotCommandResult {
    private int resultType;
    private String resultContent;

    public BotCommandResult(int resultType, String resultContent) {
        this.resultType = resultType;
        this.resultContent = resultContent;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }
}
