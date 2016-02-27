package com.release.indeepen.management.dateManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by lyo on 2015-11-11.
 */
public class DateManager {
    public static HashMap<String, Boolean> hDate = new HashMap<String, Boolean>();
    private static DateManager instance;


    private DateManager() {
    }

    public static DateManager getInstance(){
        if(null == instance){
            instance = new DateManager();
        }
        return instance;
    }

    public boolean isEnd(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        long startTime= 0L;
        long currTime= 0L;
        try {
            Date date = sdf.parse(time);
            startTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currTime = new Date(System.currentTimeMillis()).getTime();


        return  currTime> startTime? true : false;
    }



    public String getPastTime(String time){
        long createTime = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            time = time.replace("Z", "GMT+00:00");
            Date date = sdf.parse(time);
            createTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String resutl = "";
        long now = System.currentTimeMillis();

        GregorianCalendar today = new GregorianCalendar ( );
        today.setTimeInMillis(now);

        int nowYear = today.get(Calendar.YEAR);
        int nowMonth = today.get(Calendar.MONTH) + 1;
        int nowDay = today.get(Calendar.DAY_OF_MONTH);
        int nowHour = today.get(Calendar.HOUR_OF_DAY);
        int nowMin = today.get(Calendar.MINUTE);
        int nowSec = today.get(Calendar.SECOND);

        today.setTimeInMillis(createTime);
        int timeYear = today.get(Calendar.YEAR);
        int timeMonth = today.get(Calendar.MONTH) + 1;
        int timeDay = today.get(Calendar.DAY_OF_MONTH);
        int timeHour = today.get(Calendar.HOUR_OF_DAY);
        int timeMin = today.get(Calendar.MINUTE);
        int timeSec = today.get(Calendar.SECOND);

        int year = nowYear - timeYear;
        int month = nowMonth - timeMonth;
        int day = nowDay - timeDay;
        int hour = nowHour - timeHour;
        int min = nowMin - timeMin;
        int second = nowSec - timeSec;

        if(year > 1){
            resutl = year +"년 전";
           if(month > 0) {
               resutl =year +"년 "+ month + "개월 전";
           }
        }else if(month > 1){
            resutl = month +"개월 전";
        }else if(day > 1){
            resutl = day +"일 전";
        }else if(hour > 1){
            resutl = hour +"시간 전";
        }else if(min > 1){
            resutl = min +"분 전";
        }else if(second > 1){
            resutl = second +"초 전";
        }


       /* if(year > 0){
            resutl = year +"년 전";
            if(month > 0) {
                resutl =year +"년 "+ month + "개월 전";
            }
        }else if(month > 0 && day >= 0){
            resutl = month +"개월 전";
        }else if(month == 0 && day > 0){
            resutl = day +"일 전";
        }else if(month > 0 && day < 0){
            resutl = (today.getMaximum(Calendar.DAY_OF_MONTH) - timeDay) + nowDay +"일 전";
        }else if(day > 0 && hour >= 0){
            resutl = day +"일 전";
        }else if(day == 0 && hour > 0){
            resutl = hour +"시간 전";
        }else if(hour > 0 && min >= 0){
            resutl = hour +"시간 전";
        }else if(day > 0 && hour < 0){
            resutl = (24 - timeHour) + nowHour+"시간 전";
        }else if(hour == 0 && min > 0){
            resutl = min +"분 전";
        }else if(hour > 0 && min < 0){
            resutl = ((60 - timeMin) + nowMin) +"분 전";
        }else if (min > 0 && second >= 0){
            resutl = min +"분 전";
        }else if(min > 0 && second < 0){
            resutl = ((60 - timeSec) + nowSec) +"초 전";
        }else if(second > 0){
            resutl = second +"초 전";
        }
*/
        return resutl;
    }
}
