package site.mingsha.kernel.core.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;


/**
 * 现代风格日期工具类，全部基于LocalDate/LocalDateTime/Instant/DateTimeFormatter实现。
 * 推荐优先使用本类新API，旧Date相关方法已废弃。
 *
 * @author mingsha
 * @date: 2025-07-10
 */
public class DateUtils {
    // 常用格式
    public static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter YMDHM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter YMDHMS2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter YMD2 = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter HMS = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter CHINESE = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    private DateUtils() {}

    /**
     * 格式化LocalDate为yyyy-MM-dd字符串
     */
    public static String format(LocalDate date) {
        return date == null ? null : date.format(YMD);
    }

    /**
     * 格式化LocalDateTime为yyyy-MM-dd HH:mm:ss字符串
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(YMDHMS);
    }

    /**
     * 按指定pattern格式化LocalDateTime
     * @param dateTime 时间
     * @param pattern 格式
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime == null ? null : dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 按指定pattern格式化LocalDate
     * @param date 日期
     * @param pattern 格式
     */
    public static String format(LocalDate date, String pattern) {
        return date == null ? null : date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析yyyy-MM-dd字符串为LocalDate
     */
    public static LocalDate parseDate(String str) {
        return LocalDate.parse(str, YMD);
    }

    /**
     * 解析yyyy-MM-dd HH:mm:ss字符串为LocalDateTime
     */
    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, YMDHMS);
    }

    /**
     * 按指定pattern解析字符串为LocalDateTime
     * @param str 字符串
     * @param pattern 格式
     */
    public static LocalDateTime parseDateTime(String str, String pattern) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDate加天数
     */
    public static LocalDate plusDays(LocalDate date, long days) {
        return date.plusDays(days);
    }

    /**
     * LocalDateTime加分钟
     */
    public static LocalDateTime plusMinutes(LocalDateTime dt, long mins) {
        return dt.plusMinutes(mins);
    }

    /**
     * LocalDateTime加秒
     */
    public static LocalDateTime plusSeconds(LocalDateTime dt, long secs) {
        return dt.plusSeconds(secs);
    }

    /**
     * LocalDateTime加小时
     */
    public static LocalDateTime plusHours(LocalDateTime dt, long hours) {
        return dt.plusHours(hours);
    }

    /**
     * LocalDateTime加天数
     */
    public static LocalDateTime plusDays(LocalDateTime dt, long days) {
        return dt.plusDays(days);
    }

    /**
     * 计算两个LocalDate间的天数差
     */
    public static long betweenDays(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 计算两个LocalDateTime间的秒数差
     */
    public static long betweenSeconds(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.SECONDS.between(start, end);
    }

    /**
     * 计算两个LocalDateTime间的分钟数差
     */
    public static long betweenMinutes(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.MINUTES.between(start, end);
    }

    /**
     * 获取本月第一天
     */
    public static LocalDate firstDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    /**
     * 获取本月最后一天
     */
    public static LocalDate lastDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    /**
     * 获取今天
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 获取某天是周几
     */
    public static DayOfWeek getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek();
    }

    /**
     * 判断是否闰年
     */
    public static boolean isLeapYear(LocalDate date) {
        return date.isLeapYear();
    }

    /**
     * LocalDateTime转时间戳（毫秒）
     */
    public static long toEpochMilli(LocalDateTime dt) {
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间戳（毫秒）转LocalDateTime
     */
    public static LocalDateTime fromEpochMilli(long ms) {
        return Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date（兼容老API）
     */
    @Deprecated
    public static Date toDate(LocalDateTime dt) {
        return dt == null ? null : Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转LocalDateTime（兼容老API）
     */
    @Deprecated
    public static LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
