package com.futuremall.android.util;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allen on 2015/10/28.
 */
public class TimeUtils {
    private static final long ONE_MINUTE = 60;
    private static final long TEN_MINUTE = 600;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long TWO_DAY = 172800;


    public static String fromToday(String dateStr) {

        if (StringUtil.isEmpty(dateStr)) return "";
        Date originalDate = null;
        try {
            originalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);

        long time = originalDate.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago / TWO_DAY >= 1) {
            return getYearAndMonthAndDay(dateStr);
        }
        if (ago / ONE_DAY >= 1) {
            return "1天前";
        }
        if (ago / ONE_HOUR >= 1) {
            return "1小时前";
        }
        if (ago / TEN_MINUTE >= 1) {
            return "10分钟前";
        }
        if (ago / ONE_MINUTE >= 1) {
            return "1分钟前";
        }
        if (ago / ONE_MINUTE < 1) {
            return "刚刚";
        }
        return getYearAndMonthAndDay(dateStr);
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static String getTimestamp() {
        return new Timestamp(getCurrentTime()).toString();
    }

    public static String getFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    public static int getYear() {
        Date date = new Date(System.currentTimeMillis());
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.valueOf(year);
    }

    public static int getMonth() {
        Date date = new Date(System.currentTimeMillis());
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.valueOf(month);
    }

    /**
     * 时间字符串中截取时分
     * @param time
     * @return
     */
    public static String getHoursAndMinute(String time) {

        if (TextUtils.isEmpty(time)) return "";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String HMStr = new SimpleDateFormat("HH:mm").format(date);
        return HMStr;
    }

    /**
     * 年月日
     * @param
     * @return 2017-12-1
     */
    public static String getYearAndMonthAndDay(Date date) {
        String YMDStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return YMDStr;
    }

    /**
     * 时间字符串中截取年月日
     * @param time
     * @return
     */
    public static String getYearAndMonthAndDay(String time) {
        if(StringUtil.isEmpty(time)){
            return "";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String YMDStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return YMDStr;
    }

    public static String getDateForItem(String dateStart, String dataEnd) {
        Date ymd = null;
        Date secondMd = null;
        Date firstMd = null;
        String result;
        if (!TextUtils.isEmpty(dateStart) && TextUtils.isEmpty(dataEnd)){
            try {
                ymd = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);//只有开始时间或开始与结束同一天则显示开始时间
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new SimpleDateFormat("yyyy.MM.dd").format(ymd);
        }
        if (TextUtils.isEmpty(dateStart) || TextUtils.isEmpty(dataEnd)) return "";

        try {
            ymd = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
            firstMd = new SimpleDateFormat("yyyy-MM-dd").parse(dateStart);
            secondMd = new SimpleDateFormat("yyyy-MM-dd").parse(dataEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String YMDStr = new SimpleDateFormat("yyyy.MM.dd").format(ymd);
        String SecondYMDStr = new SimpleDateFormat("yyyy.MM.dd").format(secondMd);
        String firstMDStr = new SimpleDateFormat("MM.dd").format(firstMd);
        String secondMDStr = new SimpleDateFormat("MM.dd").format(secondMd);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(firstMd);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(secondMd);

        if (firstMDStr.equals(secondMDStr)){
            result = YMDStr;
        }else if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            result = YMDStr + " - " + secondMDStr;
        }else {
            result = YMDStr + " - " + SecondYMDStr;
        }
        return result;
    }


    public static class TimeCount extends CountDownTimer {

        TextView mTextView;
        public TimeCount(long millisInFuture, long countDownInterval, TextView textView) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.mTextView = textView;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            mTextView.setText("获取验证码");
            mTextView.setClickable(true);
            mTextView.setSelected(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            mTextView.setClickable(false);
            mTextView.setText(millisUntilFinished / 1000 + "秒后重新获取");
            mTextView.setSelected(true);
        }
    }

    public static String replaceTime(String str){

        if (StringUtil.isEmpty(str)){
            return "";
        }else {
            return str.replaceAll("-", ".");
        }
    }

}
