package site.mingsha.kernel.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class WeekUtils {

    private static final Logger logger = LoggerFactory.getLogger(WeekUtils.class);

    // 星期常量（ISO标准：周一=1 到 周日=7）
    public static final DayOfWeek MONDAY = DayOfWeek.MONDAY;
    public static final DayOfWeek TUESDAY = DayOfWeek.TUESDAY;
    public static final DayOfWeek WEDNESDAY = DayOfWeek.WEDNESDAY;
    public static final DayOfWeek THURSDAY = DayOfWeek.THURSDAY;
    public static final DayOfWeek FRIDAY = DayOfWeek.FRIDAY;
    public static final DayOfWeek SATURDAY = DayOfWeek.SATURDAY;
    public static final DayOfWeek SUNDAY = DayOfWeek.SUNDAY;

    //-------------------------- 新增快捷方法 --------------------------

    /**
     * 判断是否是周末（周六或周日）
     *
     * @param date 日期对象（不可为空）
     */
    public static boolean isWeekend(LocalDate date) {
        logger.debug("判断日期 [{}] 是否是周末", date);
        Objects.requireNonNull(date, "日期参数不能为null");

        DayOfWeek day = date.getDayOfWeek();
        boolean result = day == SATURDAY || day == SUNDAY;
        logger.debug("日期 [{}] 周末判断结果: {}", date, result);
        return result;
    }

    /**
     * 判断今天是否是周末
     */
    public static boolean isWeekend() {
        return isWeekend(LocalDate.now());
    }

    /**
     * 判断是否是工作日（周一到周五）
     *
     * @param date 日期对象（不可为空）
     */
    public static boolean isWorkingDay(LocalDate date) {
        logger.debug("判断日期 [{}] 是否是工作日", date);
        Objects.requireNonNull(date, "日期参数不能为null");

        boolean result = !isWeekend(date);
        logger.debug("日期 [{}] 工作日判断结果: {}", date, result);
        return result;
    }

    /**
     * 判断今天是否是工作日
     */
    public static boolean isWorkingDay() {
        return isWorkingDay(LocalDate.now());
    }

    //-------------------------- 快捷方法 --------------------------

    /**
     * 获取当前日期的星期名称（默认系统语言环境）
     */
    public static String getWeekdayName() {
        return getWeekdayName(LocalDate.now(), TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 获取指定日期的星期名称（默认系统语言环境）
     */
    public static String getWeekdayName(LocalDate date) {
        return getWeekdayName(date, TextStyle.FULL, Locale.getDefault());
    }

    /**
     * 获取当前日期的星期名称（自定义语言环境）
     */
    public static String getWeekdayName(TextStyle style, Locale locale) {
        return getWeekdayName(LocalDate.now(), style, locale);
    }

    /**
     * 完整参数版核心方法
     *
     * @param date   日期（不可为空）
     * @param style  文本格式（如FULL/SHORT）
     * @param locale 语言环境（如Locale.CHINA/Locale.US）
     */
    public static String getWeekdayName(LocalDate date, TextStyle style, Locale locale) {
        Objects.requireNonNull(date, "日期不可为空");
        return date.getDayOfWeek().getDisplayName(style, locale);
    }

    /**
     * 获取ISO标准星期数值（1=星期一 到 7=星期日）
     */
    public static int getISODayValue() {
        return getISODayValue(LocalDate.now());
    }

    /**
     * 获取指定日期的ISO标准星期数值
     */
    public static int getISODayValue(LocalDate date) {
        Objects.requireNonNull(date, "日期不可为空");
        return date.getDayOfWeek().getValue();
    }

    //-------------------------- 快捷方法 --------------------------

    /**
     * 获取中文全称（如：星期一）
     */
    public static String getChineseFullWeekday() {
        return getWeekdayName(TextStyle.FULL, Locale.CHINA);
    }

    /**
     * 获取中文简称（如：周一）
     */
    public static String getChineseShortWeekday() {
        return getWeekdayName(TextStyle.SHORT, Locale.CHINA);
    }

    /**
     * 获取英文全称（如：Monday）
     */
    public static String getEnglishFullWeekday() {
        return getWeekdayName(TextStyle.FULL, Locale.US);
    }

    /**
     * 获取英文简称（如：Mon）
     */
    public static String getEnglishShortWeekday() {
        return getWeekdayName(TextStyle.SHORT, Locale.US);
    }

}
