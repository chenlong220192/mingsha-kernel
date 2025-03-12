package site.mingsha.kernel.core.utils;

/**
 * 异常栈截取
 * 
 * @author Ming Sha
 * @create: 2020-05-20 16:14
 */
public class ExceptionUtils {

    /**
     * 截取1M长度的异常栈信息
     * 
     * @param e
     * @return
     */
    public static String extract(Exception e) {
        String error = org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e);
        int maxLength = 1024 * 1024;
        int length = error.length() > maxLength ? maxLength : error.length();
        return error.substring(1, length);
    }

}