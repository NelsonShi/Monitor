package com.fast.bpserver.utils;

import java.util.Date;

/**
 * Created by Nelson on 2019/12/17.
 */
public class TimeZoneUtil {
    /**
     * 将input 时间 转成当前时区时间
     * @param inputDate
     * @param timeSpan
     * @return
     */
     public static Date formateDateToZone(Date inputDate,Integer timeSpan){
         return new Date(inputDate.getTime()-timeSpan*60*60*1000);
     }
}
