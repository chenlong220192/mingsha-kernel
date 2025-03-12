package site.mingsha.kernel.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import site.mingsha.kernel.logger.LogUtils;
import site.mingsha.kernel.logger.pattern.LogPattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ming Sha
 * @create: 2020-05-20 16:07
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final long ONE_DAY_SECONDS = 86400L;
    public static final long ONE_DAY_MILL_SECONDS = 86400000L;

    public static final String shortFormat = "yyyyMMdd";
    public static final String longFormat = "yyyyMMddHHmmss";
    public static final String webFormat = "yyyy-MM-dd";
    public static final String timeFormat = "HHmmss";
    public static final String monthFormat = "yyyyMM";
    public static final String chineseDtFormat = "yyyy年MM月dd日";
    public static final String newFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String noSecondFormat = "yyyy-MM-dd HH:mm";
    public static final String timeV16Format = "yyyy-MM-dd'T'HH:mm:ss";

    private DateUtils() {
    }

    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        return df;
    }

    public static String format(Date date, String format) {
        return date == null ? null : (new SimpleDateFormat(format)).format(date);
    }

    /**
     * 13位时间戳转换
     *
     * @param time
     * @return
     */
    public static Date timeStamp13Date(long time) {
        try {
            SimpleDateFormat simple = new SimpleDateFormat(newFormat);
            return simple.parse(simple.format(time));
        } catch (ParseException e) {
            LogUtils.info(String.format(LogPattern.ERROR_NO_RESP, System.currentTimeMillis(), time, e.getMessage()));
            return null;
        }
    }

    /**
     * 时间戳转时间(10位时间戳)
     *
     * @param time
     * @return
     */
    public static Date timestampToDate(long time) throws Exception {
        String dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(newFormat);
        long timeLong = Long.valueOf(time);
        dateTime = simpleDateFormat.format(new Date(timeLong * 1000L));
        return simpleDateFormat.parse(dateTime);
    }

    /**
     * 格林威治时间格式转换为北京时间格式
     * 支持jdk1.6的写法<br/>
     * 解析2020-04-27T19:13:01.438394248+08:00格式类型的时间<br/>
     * 将2020-04-27T19:13:01.438394248+08:00转换成2020-04-27 19:13:01<br/>
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getTimestampTimeV16(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(timeV16Format);
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        sdf.setTimeZone(tz);
        return sdf.parse(str);
    }

    public static Date parseDateNoTime(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (sDate != null && sDate.length() >= "yyyyMMdd".length()) {
            if (!StringUtils.isNumeric(sDate)) {
                throw new ParseException("not all digit", 0);
            } else {
                return dateFormat.parse(sDate);
            }
        } else {
            throw new ParseException("length too little", 0);
        }
    }

    public static Date parseDateNoTime(String sDate, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        } else {
            DateFormat dateFormat = new SimpleDateFormat(format);
            if (sDate != null && sDate.length() >= format.length()) {
                return dateFormat.parse(sDate);
            } else {
                throw new ParseException("length too little", 0);
            }
        }
    }

    public static Date parseDateNoTimeWithDelimit(String sDate, String delimit) throws ParseException {
        sDate = sDate.replaceAll(delimit, "");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (sDate != null && sDate.length() == "yyyyMMdd".length()) {
            return dateFormat.parse(sDate);
        } else {
            throw new ParseException("length not match", 0);
        }
    }

    public static Date parseDateLongFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d = null;
        if (sDate != null && sDate.length() == "yyyyMMddHHmmss".length()) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException var4) {
                return null;
            }
        }

        return d;
    }

    public static Date parseDateNewFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        dateFormat.setLenient(false);
        if (sDate != null && sDate.length() == "yyyy-MM-dd HH:mm:ss".length()) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException var4) {
                return null;
            }
        }

        return d;
    }

    public static Date addHours(Date date, long hours) {
        return addMinutes(date, hours * 60L);
    }

    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, minutes * 60L);
    }

    public static Date addSeconds(Date date1, long secs) {
        return new Date(date1.getTime() + secs * 1000L);
    }

    public static boolean isValidHour(String hourStr) {
        if (!StringUtils.isEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
            int hour = new Integer(hourStr);
            if (hour >= 0 && hour <= 23) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidMinuteOrSecond(String str) {
        if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
            int hour = new Integer(str);
            if (hour >= 0 && hour <= 59) {
                return true;
            }
        }

        return false;
    }

    public static Date addDays(Date date1, long days) {
        return addSeconds(date1, days * 86400L);
    }

    public static String getTomorrowDateString(String sDate) throws ParseException {
        Date aDate = parseDateNoTime(sDate);
        aDate = addSeconds(aDate, 86400L);
        return getDateString(aDate);
    }

    public static String getLongDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return getDateString(date, dateFormat);
    }

    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateString(date, dateFormat);
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        return date != null && dateFormat != null ? dateFormat.format(date) : null;
    }

    public static String getYesterDayDateString(String sDate) throws ParseException {
        Date aDate = parseDateNoTime(sDate);
        aDate = addSeconds(aDate, -86400L);
        return getDateString(aDate);
    }

    public static String getDateString(Date date) {
        DateFormat df = getNewDateFormat("yyyyMMdd");
        return df.format(date);
    }

    public static String getWebDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat("yyyy-MM-dd");
        return getDateString(date, dateFormat);
    }

    public static String getChineseDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat("yyyy年MM月dd日");
        return getDateString(date, dateFormat);
    }

    public static String getTodayString() {
        DateFormat dateFormat = getNewDateFormat("yyyyMMdd");
        return getDateString(new Date(), dateFormat);
    }

    public static String getTimeString(Date date) {
        DateFormat dateFormat = getNewDateFormat("HHmmss");
        return getDateString(date, dateFormat);
    }

    public static String getBeforeDayString(int days) {
        Date date = new Date(System.currentTimeMillis() - 86400000L * (long) days);
        DateFormat dateFormat = getNewDateFormat("yyyyMMdd");
        return getDateString(date, dateFormat);
    }

    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000L;
    }

    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 60000L;
    }

    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 86400000L;
    }

    public static String getBeforeDayString(String dateString, int days) {
        DateFormat df = getNewDateFormat("yyyyMMdd");

        Date date;
        try {
            date = df.parse(dateString);
        } catch (ParseException var5) {
            date = new Date();
        }

        date = new Date(date.getTime() - 86400000L * (long) days);
        return df.format(date);
    }

    public static boolean isValidShortDateFormat(String strDate) {
        if (strDate.length() != "yyyyMMdd".length()) {
            return false;
        } else {
            try {
                Integer.parseInt(strDate);
            } catch (Exception var4) {
                return false;
            }

            DateFormat df = getNewDateFormat("yyyyMMdd");

            try {
                df.parse(strDate);
                return true;
            } catch (ParseException var3) {
                return false;
            }
        }
    }

    public static boolean isValidShortDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");
        return isValidShortDateFormat(temp);
    }

    public static boolean isValidLongDateFormat(String strDate) {
        if (strDate.length() != "yyyyMMddHHmmss".length()) {
            return false;
        } else {
            try {
                Long.parseLong(strDate);
            } catch (Exception var4) {
                return false;
            }

            DateFormat df = getNewDateFormat("yyyyMMddHHmmss");

            try {
                df.parse(strDate);
                return true;
            } catch (ParseException var3) {
                return false;
            }
        }
    }

    public static boolean isValidLongDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");
        return isValidLongDateFormat(temp);
    }

    public static String getShortDateString(String strDate) {
        return getShortDateString(strDate, "-|/");
    }

    public static String getShortDateString(String strDate, String delimiter) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        } else {
            String temp = strDate.replaceAll(delimiter, "");
            return isValidShortDateFormat(temp) ? temp : null;
        }
    }

    public static String getShortFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();
        cal.setTime(dt);
        cal.set(5, 1);
        DateFormat df = getNewDateFormat("yyyyMMdd");
        return df.format(cal.getTime());
    }

    public static String getWebTodayString() {
        DateFormat df = getNewDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public static String getWebFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();
        cal.setTime(dt);
        cal.set(5, 1);
        DateFormat df = getNewDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
        try {
            Date date = formatIn.parse(dateString);
            return formatOut.format(date);
        } catch (ParseException var4) {
            logger.warn("convert() --- orign date error: " + dateString);
            return "";
        }
    }

    public static String convert2WebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat("yyyyMMdd");
        DateFormat df2 = getNewDateFormat("yyyy-MM-dd");
        return convert(dateString, df1, df2);
    }

    public static String convert2ChineseDtFormat(String dateString) {
        DateFormat df1 = getNewDateFormat("yyyyMMdd");
        DateFormat df2 = getNewDateFormat("yyyy年MM月dd日");
        return convert(dateString, df1, df2);
    }

    public static String convertFromWebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat("yyyyMMdd");
        DateFormat df2 = getNewDateFormat("yyyy-MM-dd");
        return convert(dateString, df2, df1);
    }

    public static boolean webDateNotLessThan(String date1, String date2) {
        DateFormat df = getNewDateFormat("yyyy-MM-dd");
        return dateNotLessThan(date1, date2, df);
    }

    public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            return !d1.before(d2);
        } catch (ParseException var5) {
            logger.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
            return false;
        }
    }

    public static String getEmailDate(Date today) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        String todayStr = sdf.format(today);
        return todayStr;
    }

    public static String getSmsDate(Date today) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");
        String todayStr = sdf.format(today);
        return todayStr;
    }

    public static String formatTimeRange(Date startDate, Date endDate, String format) {
        if (endDate != null && startDate != null) {
            String rt = null;
            long range = endDate.getTime() - startDate.getTime();
            long day = range / 86400000L;
            long hour = range % 86400000L / 3600000L;
            long minute = range % 3600000L / 60000L;
            if (range < 0L) {
                day = 0L;
                hour = 0L;
                minute = 0L;
            }

            rt = format.replaceAll("dd", String.valueOf(day));
            rt = rt.replaceAll("hh", String.valueOf(hour));
            rt = rt.replaceAll("mm", String.valueOf(minute));
            return rt;
        } else {
            return null;
        }
    }

    public static String formatMonth(Date date) {
        return date == null ? null : (new SimpleDateFormat("yyyyMM")).format(date);
    }

    public static Date getBeforeDate() {
        Date date = new Date();
        return new Date(date.getTime() - 86400000L);
    }

    public static Date getDayBegin(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);
        String dateString = df.format(date);

        try {
            return df.parse(dateString);
        } catch (ParseException var4) {
            return date;
        }
    }

    public static boolean dateLessThanNowAddMin(Date date, long min) {
        return addMinutes(date, min).before(new Date());
    }

    public static boolean isBeforeNow(Date date) {
        if (date == null) {
            return false;
        } else {
            return date.compareTo(new Date()) < 0;
        }
    }

}
