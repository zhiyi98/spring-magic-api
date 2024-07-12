package cn.gojava.app.common.utils.date;

import cn.hutool.core.date.*;

import java.util.Date;

/**
 * 日期时间工具类
 *
 * @see LocalDateTimeUtil java8日志工具类
 * @see DatePattern 日期常用格式工具类
 */
public class DateUtils extends DateUtil {

    /**
     * 将日期转换为Date对象
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss格式的日期字符串
     * @return 日期对象
     */
    public static DateTime parse(CharSequence dateStr) {
        return new DateTime(dateStr, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date 被格式化的日期
     * @return yyyy-MM-dd HH:mm:ss格式的日期字符串
     * @since 5.0.0
     */
    public static String format(Date date) {
        return TemporalAccessorUtil.format(date.toInstant(), DatePattern.NORM_DATETIME_PATTERN);
    }

}
