package com.xiaoqqq.l_time.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.utils
 * @date 2019-07-06
 * @describe 日期工具类
 */
public class DateUtils {

    /**
     * 获取两个日期之间相差的天数
     *
     * @param startDate   开始的日期
     * @param currentDate 当前的日期
     * @return 两者之间相隔的天数
     */
    public static int getDaysByDate(Date startDate, Date currentDate) {
        final long startDateSecond = startDate.getTime() / 1000;
        final long currentDateSecond = currentDate.getTime() / 1000;
        final long days = (currentDateSecond - startDateSecond) / 3600 / 24;
        return (int) days;
    }

    public static int getDaysByMillions(Long startMillions, Long currentMillions) {
        final long days = (currentMillions - startMillions) / 1000 / 3600 / 24;
        return (int) days;
    }

    /**
     * 日期格式的字符串转成毫秒值
     *
     * @param dateString
     * @return
     */
    public static String getDateMillions(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date != null ? date.getTime() : 0);
    }

    /**
     * 把时间戳转换为毫秒
     *
     * @param str
     * @return
     */
    public static Long dateToStamp(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long msTime = -1;
        try {
            msTime = simpleDateFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return msTime;

    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeStamp
     * @return
     */
    public static String stampToDate(String timeStamp) {
        String res = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            res = simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        } catch (Exception e) {
            Log.e("xiaoqqq", e.getMessage());
        }
        return res;
    }
}
