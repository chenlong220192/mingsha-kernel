package site.mingsha.kernel.core.utils;

/**
 * 异常栈截取
 * 
 * @author mingsha
 * @date: 2025-07-10
 */
public class ExceptionUtils {

    /**
     * 截取1M长度的异常栈信息
     *
     * @param e 异常对象
     * @return 异常堆栈字符串，最大1M
     */
    public static String extract(Exception e) {
        String error = org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e);
        int maxLength = 1024 * 1024;
        int length = error.length() > maxLength ? maxLength : error.length();
        return error.substring(0, length);
    }

}