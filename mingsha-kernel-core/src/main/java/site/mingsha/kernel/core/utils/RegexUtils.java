package site.mingsha.kernel.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author mingsha
 * @date: 2025-07-10
 */
public class RegexUtils {

    private static String MOBILE    = "^((\\d{3}))?1[3,5,8,7,4][0-9]\\d{8}";
    private static String EMAIL     = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private static String IP        = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    private static String TELEPHONE = "0\\d{2,3}-\\d{7,8}|0\\d{4}-\\d{7,8}";
    private static String DATE      = "(19|20)\\d\\d-(0[1-9]|1[0-2])-([0][1-9]|[1,2][0-9]|3[0-1])";
    private static String FULL_DATE = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))";
    private static String AGE       = "120|((1[0-1]|\\d)?\\d)";
    private static String CERT      = "[\\d]{6}(19)?[\\d]{2}((0[1-9])|(10|11|12))([012][\\d]|(30|31))[\\d]{3}[xX\\d]*";
    private static String MONEY     = "^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";

    public RegexUtils() {
    }

    public static boolean emailMatch(String email) {
        return StringUtils.isNotEmpty(email) ? email.matches(EMAIL) : false;
    }

    public static boolean mobileMacth(String mobile) {
        return StringUtils.isNotEmpty(mobile) ? mobile.matches(MOBILE) : false;
    }

    public static boolean ipMacth(String ip) {
        return StringUtils.isNotEmpty(ip) ? ip.matches(IP) : false;
    }

    public static boolean telMacth(String tel) {
        return StringUtils.isNotEmpty(tel) ? tel.matches(TELEPHONE) : false;
    }

    public static boolean simpleDateMatch(String date) {
        return StringUtils.isNotEmpty(date) ? date.matches(DATE) : false;
    }

    public static boolean dateMatch(String date) {
        return StringUtils.isNotEmpty(date) ? date.matches(FULL_DATE) : false;
    }

    public static boolean ageMatch(String age) {
        return StringUtils.isNotEmpty(age) ? age.matches(AGE) : false;
    }

    public static boolean simpleCertMatch(String cert) {
        return StringUtils.isNotEmpty(cert) ? cert.matches(CERT) : false;
    }

    public static boolean moneyMatch(String money) {
        return StringUtils.isNotEmpty(money) ? money.matches(MONEY) : false;
    }
}
