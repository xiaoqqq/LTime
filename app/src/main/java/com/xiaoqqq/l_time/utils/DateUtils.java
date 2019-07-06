package com.xiaoqqq.l_time.utils;

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
}
