package com.fast.monitorserver.entity.vo;

import com.fast.bpserver.entity.vo.SessionControlResult;

import java.util.List;

/**
 * Created by Nelson on 2020/1/20.
 */
public class PendingProcessRes {
    //1 全部通讯成功 0：异常
   private Integer status;
   private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PendingProcessRes() {
        this.message = "";
        this.status=1;
    }
}
