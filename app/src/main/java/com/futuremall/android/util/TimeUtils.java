package com.futuremall.android.util;

import android.os.CountDownTimer;
import android.widget.TextView;

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
    /**
     * 时间戳转日期 14000556 2017-10-1
     * @param time
     * @return
     */
    public static String timesTwo(long time) {

        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        String times = sdr.format(new Date(time));
        return times;

    }

    /**
     * 年月日
     * @param
     * @return 2017-12-1
     */
    public static String getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        String YMDStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return YMDStr;
    }

    /**
     * 日期转时间戳 2017-10-1 14000556
     * @return
     */
    public static long dataOne(Date date) {

        return date.getTime();
    }


    public static int getYear(long time) {
        Date date = new Date(time >0? time: System.currentTimeMillis());
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.valueOf(year);
    }

    public static int getMonth(long time) {
        Date date = new Date(time >0? time: System.currentTimeMillis());
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.valueOf(month);
    }

    public static int getDay(long time) {
        Date date = new Date(time >0? time: System.currentTimeMillis());
        String day = new SimpleDateFormat("dd").format(date);
        return Integer.valueOf(day);
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

}
