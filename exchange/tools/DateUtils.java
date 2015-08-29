package com.deeal.exchange.tools;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * used to change data form
 * Created by Administrator on 2015/7/7.
 */
public class DateUtils {
    /**
     * @param
     * @return data  2015-7-6-16:38
     */
    public static String getData() {
        String data = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        data = formatter.format(curDate);
        return data;
    }

    public static String FormatData(String data){
        String result ;
        long publishdate = 0;
        try {
            SimpleDateFormat df = (SimpleDateFormat) java.text.DateFormat.getDateInstance();
            df.applyPattern("yyyy-MM-dd HH:mm:ss");
            publishdate = df .parse(data).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long distance = System.currentTimeMillis() - publishdate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        if(distance < 60*60*1000 ){
            result =  distance/1000/60+"分钟前";
        }else if(distance <24*60*60*1000){
            result =  distance/1000/60/60+"小时前";
        }else if(distance <60*60*24*7*1000){
            result =  distance/1000/60/60/24+"天前";
        }else{
            result = data;
        }
        return  result;
    }


}
