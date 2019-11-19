package com.blueprismserver.utils.ComputationalModule;

import com.blueprismserver.entity.vo.ScheduleTimeSlot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nelson on 2019/11/13.
 * schedule时间段计算模型
 * 系统时间为UTC 时间
 */
public class ScheduleTimeSlotModule {
    //指定时间段开始时间
   private Date inputStartDate;
   // 指定时间段 截止时间
   private Date inputEndDate;
   //schedule 第一次触发时间
   private Date triigerStartTime;
   //请换算成秒
   private long scheduleRunTime;
   //间隔数量 更具schedule Type 会自动计算
   private Integer period;
   //0 onece 1 hourly 2 daily 3 weekly 4 monthly 5 yealy 6 minitely
   private Integer scheduleType;
   //指定时间段内是否需要运行
   private Boolean needToStart;
   //指定时间段内运行次数
   private Integer runCounts;
   //指定时间段内 开始运行的世界(距离inputStartDate的秒数)
   private Integer beginToRun;
   //上次运行是否持续到指定时间段内
   private Boolean lastToToday;
   //上次运行在指定时间段内的结束时间(距离inputStartDate的秒数)
   private Integer lastEndTime;
   // 1 计算成功 -1 报错
   private Integer result;
   //报错结果
   private String resultStr;
   //schedule betweenTime 的起始时间（秒数） 距离凌晨
   private Integer startPoint;
    //schedule betweenTime 的截止时间（秒数） 距离凌晨
   private Integer endPoint;

   private List<ScheduleTimeSlot> scheduleSlotList;

    public Date getInputStartDate() {
        return inputStartDate;
    }

    public void setInputStartDate(Date inputStartDate) {
        this.inputStartDate = inputStartDate;
    }

    public Date getInputEndDate() {
        return inputEndDate;
    }

    public void setInputEndDate(Date inputEndDate) {
        this.inputEndDate = inputEndDate;
    }

    public Date getTriigerStartTime() {
        return triigerStartTime;
    }

    public void setTriigerStartTime(Date triigerStartTime) {
        this.triigerStartTime = triigerStartTime;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Boolean getNeedToStart() {
        return needToStart;
    }

    public void setNeedToStart(Boolean needToStart) {
        this.needToStart = needToStart;
    }

    public Integer getRunCounts() {
        return runCounts;
    }

    public void setRunCounts(Integer runCounts) {
        this.runCounts = runCounts;
    }

    public Integer getBeginToRun() {
        return beginToRun;
    }

    public void setBeginToRun(Integer beginToRun) {
        this.beginToRun = beginToRun;
    }

    public Boolean getLastToToday() {
        return lastToToday;
    }

    public void setLastToToday(Boolean lastToToday) {
        this.lastToToday = lastToToday;
    }

    public Integer getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(Integer lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getResultStr() {
        return resultStr;
    }

    public long getScheduleRunTime() {
        return scheduleRunTime;
    }

    public void setScheduleRunTime(long scheduleRunTime) {
        this.scheduleRunTime = scheduleRunTime;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public List<ScheduleTimeSlot> getScheduleSlotList() {
        return scheduleSlotList;
    }

    public void setScheduleSlotList(List<ScheduleTimeSlot> scheduleSlotList) {
        this.scheduleSlotList = scheduleSlotList;
    }

    public Integer getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Integer startPoint) {
        this.startPoint = startPoint;
    }

    public Integer getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Integer endPoint) {
        this.endPoint = endPoint;
    }

    public ScheduleTimeSlotModule(Date inputStartDate, Date inputEndDate, Date triigerStartTime, long scheduleRunTime, Integer period, Integer scheduleType, Integer startPoint, Integer endPoint){
          this.inputStartDate=inputStartDate;
          this.inputEndDate=inputEndDate;
          this.triigerStartTime=triigerStartTime;
          this.scheduleRunTime=scheduleRunTime;
          this.period=period;
          this.scheduleType=scheduleType;
          this.startPoint=startPoint;
          this.endPoint=endPoint;
          GenrateRunntimeBetweentInputTimes();
    }

    private void GenrateRunntimeBetweentInputTimes(){
        if(this.inputEndDate==null||this.inputStartDate==null||triigerStartTime==null||scheduleRunTime<0
                ||this.period==null||this.scheduleType==null){
            this.result=-1;
            this.resultStr="lack of necessary parameters";
            needToStart=false;
            return;
        }
        //如果类型是 minutely hourly daily weekly 定时周期
       if(scheduleType==1||scheduleType==2||scheduleType==3||scheduleType==6){
           CreateTimeSlotsWithPeriod();
           return;
       }
        //如果类型是 once monthly yealy 不定时周期（时间间隔不固定）
       if(scheduleType==0||scheduleType==4||scheduleType==5){
           CreateTimeSlotsMonthlyAndYealyAndOnece();
           return;
       }
       this.result=2;
       needToStart=false;
       this.resultStr="unkonw schedule type";
    }

    //最小计算单位是秒 此计算只支持 mintely hourly daily weekly
    private void CreateTimeSlotsWithPeriod(){
        //计算出 周期间隔 单位是秒
        long intervalTime=period*60;
        switch (scheduleType){
            case 1:intervalTime=intervalTime*60;
                   break;
            case 2:intervalTime=intervalTime*60*24;
                   break;
            case 3:intervalTime=intervalTime*60*24*7;
                   break;
            default:break;
        }
        //计算出 起始时间距离第一次执行的 时间跨度
        long betweenTime=((inputStartDate.getTime()-triigerStartTime.getTime())/1000);
        long remainder=betweenTime%intervalTime;
        // 计算出最近一次执行时间 距离 inputSatrtTime的秒数 如果betweent time 为负数 则trigger 为最近的执行时间
        long beginTime=intervalTime-remainder;
        if(betweenTime<0&&(inputEndDate.getTime()>triigerStartTime.getTime())){
            beginTime=(int)Math.ceil((triigerStartTime.getTime()-inputStartDate.getTime())*1.0/1000);
        }
       if(beginTime-((inputEndDate.getTime()-inputStartDate.getTime())/1000)>0){
           needToStart=false;
           result=-1;
           return;
       }else {
           needToStart=true;
           result=1;
           beginToRun=new Long(beginTime).intValue();
       }
       lastToToday=remainder>=0?scheduleRunTime-remainder>0:false;
       if(lastToToday){
           lastEndTime=new Long(scheduleRunTime-remainder).intValue();
       }
       long timeSpan=inputEndDate.getTime()-inputStartDate.getTime();
       double count=(timeSpan*1.0/1000)/intervalTime;
       // 计算出指定时间段内的 执行次数
        runCounts=(int)(Math.ceil(count));
    }

    //最小计算单位是秒 此计算只支持 once monthly yearly
    private void CreateTimeSlotsMonthlyAndYealyAndOnece(){
        long intervalTime=period;
        switch (scheduleType){
            case 5:intervalTime=intervalTime*12;
                break;
            case 0:intervalTime=1;
            default:break;
        }
        Calendar beginCalendar=Calendar.getInstance();
        Calendar endCalendar=Calendar.getInstance();
        Calendar beginForcount=Calendar.getInstance();
        beginForcount.setTime(inputStartDate);
        Calendar triigerStartCalendar=Calendar.getInstance();
        beginCalendar.setTime(inputStartDate);
        endCalendar.setTime(inputEndDate);
        triigerStartCalendar.setTime(triigerStartTime);
        int startMonthBetween=(beginCalendar.get(Calendar.YEAR)-triigerStartCalendar.get(Calendar.YEAR))*12+(beginCalendar.get(Calendar.MONTH)-triigerStartCalendar.get(Calendar.MONTH));
        int endMonthBetween=(endCalendar.get(Calendar.YEAR)-triigerStartCalendar.get(Calendar.YEAR))*12+(endCalendar.get(Calendar.MONTH)-triigerStartCalendar.get(Calendar.MONTH));
        if(startMonthBetween%intervalTime==0||endMonthBetween%intervalTime==0){
            int betweenDays=(int)Math.floor((inputEndDate.getTime()-inputStartDate.getTime())/(1000*60*60*24));
            List<Integer> days=new ArrayList<>();
            days.add(beginForcount.get(Calendar.DAY_OF_MONTH));
            for (int i=1;i<=betweenDays;i++){
                beginForcount.add(Calendar.DAY_OF_MONTH,1);
                int nextDay=beginForcount.get(Calendar.DAY_OF_MONTH);
                if(!days.contains(nextDay)){
                    days.add(nextDay);
                }
            }
            if(!days.contains(endCalendar.get(Calendar.DAY_OF_MONTH))){
                days.add(endCalendar.get(Calendar.DAY_OF_MONTH));
            }
            //是否在其中的一天 如果在 只需判断是时分 是否包含触发
            if(days.contains(triigerStartCalendar.get(Calendar.DAY_OF_MONTH))){
                //当天的分钟数
                int beginMintes=beginCalendar.get(Calendar.MINUTE)+beginCalendar.get(Calendar.HOUR_OF_DAY)*60;
                //此次结束 结束的分钟数
                int endMintes=beginMintes+((int)Math.floor((inputEndDate.getTime()-inputStartDate.getTime())/(1000*60)));
                //距离指定时间段 触发的分钟数
                int startMints=triigerStartCalendar.get(Calendar.MINUTE)+triigerStartCalendar.get(Calendar.HOUR_OF_DAY)*60;
                if(startMints>=beginMintes&&startMints<=endMintes){
                    needToStart=true;
                    beginToRun=(startMints-beginMintes)*60;
                    runCounts=1;
                }else {
                    needToStart=false;
                }
            }else {needToStart=false;}
            result=1;
        }else {
            needToStart=false;
            result=-1;
            return;
        }
    }

    public void GenrateScheduleList(String processName, SimpleDateFormat sdf,String triggerName){
        if(!needToStart){
            result=-1;
            return;
        };
        int intervalTime=period;
        switch (scheduleType){
            case 1:intervalTime=intervalTime*60;
                break;
            case 2:intervalTime=intervalTime*60*24;
                break;
            case 3:intervalTime=intervalTime*60*24*7;
                break;
            default:break;
        }
        long length=inputEndDate.getTime()-inputStartDate.getTime();
        long runTime=scheduleRunTime*1000;
        DecimalFormat sd=new DecimalFormat("0.00");
        String width=sd.format((runTime*100.0)/(length*1.0)<1?1:(runTime*100.0)/(length*1.0));
//        String marginLeft=sd.format((intervalTime*60*1000-runTime)*100.0/(length*1.0));
        scheduleSlotList=new ArrayList<>();
          for(int i=0;i<runCounts;i++){
              ScheduleTimeSlot vo=new ScheduleTimeSlot();
              vo.setTrggerName(triggerName);
              int beginMinutes=beginToRun/60+intervalTime*i;
              Integer beginSecondsFormZero=ComputeSeconds(beginMinutes);
              if(beginSecondsFormZero<startPoint||beginSecondsFormZero>endPoint)continue;
              if(beginMinutes*60*1000>=length)continue;
              vo.setSatrtMinutes(beginMinutes);
              int endMinutes=beginMinutes+new Long(scheduleRunTime/60).intValue();
              if(endMinutes*60*1000>=length){
                  endMinutes=new Long(length/(60*1000)).intValue();
              }
              vo.setEndMinutes(endMinutes);
              vo.setProcessName(processName);
              String spanBegin=sdf.format(new Date(inputStartDate.getTime()+beginMinutes*60*1000));
              String spanEnd=(inputStartDate.getTime()+endMinutes*60*1000)>=inputEndDate.getTime()?sdf.format(inputEndDate):sdf.format(new Date(inputStartDate.getTime()+endMinutes*60*1000));
              vo.setTimeSpan(spanBegin+"--"+spanEnd);
              vo.setWidth(width);
//              vo.setMarginLeft(marginLeft);
              scheduleSlotList.add(vo);
          }
          if(lastToToday!=null&&lastToToday){
              ScheduleTimeSlot vo=new ScheduleTimeSlot();
              vo.setTrggerName(triggerName);
//              vo.setMarginLeft("0");
              String width1=sd.format(lastEndTime*100000.0/(length*1.0)<1?1:lastEndTime*100000.0/(length*1.0));
              int endMinite=lastEndTime/60;
              if(lastEndTime*1000>=length){
                  endMinite=new Long(length/(1000*60)).intValue();
              }
              vo.setWidth(width1);
              vo.setSatrtMinutes(0);
              vo.setProcessName(processName);
              vo.setEndMinutes(endMinite);
              String spanBegin=sdf.format(new Date(inputStartDate.getTime()));
              String spanEnd=(inputStartDate.getTime()+lastEndTime*1000)>=inputEndDate.getTime()?sdf.format(inputEndDate):sdf.format(new Date(inputStartDate.getTime()+lastEndTime*1000));
              vo.setTimeSpan(spanBegin+"--"+spanEnd);
              scheduleSlotList.add(vo);
          }
    }

    /*
      scheduleBeginTime: 单位是分
      计算出开始时间的秒数（距离当天凌晨）
    * */
    private Integer ComputeSeconds(Integer scheduleBeginTime){
       Date beginTime=new Date(inputStartDate.getTime()+scheduleBeginTime*1000*60);
       Calendar calendar=Calendar.getInstance();
       calendar.setTime(beginTime);
       Integer seconds=calendar.get(Calendar.HOUR_OF_DAY)*60*60+calendar.get(Calendar.MINUTE)*60+calendar.get(Calendar.SECOND);
       return seconds;
    }

}
