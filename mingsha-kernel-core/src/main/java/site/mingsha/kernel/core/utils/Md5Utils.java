/**
 * Creation Date:2016年1月19日-上午11:13:36
 * 
 * Copyright 2008-2016 © 同程网 Inc. All Rights Reserved
 */
package site.mingsha.kernel.core.utils;

import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mingsha
 * @date: 2025-07-10
 */
public class Md5Utils {

    private static final Logger logger = LoggerFactory.getLogger(Md5Utils.class);

    /**
     * 工具类构造方法私有化，防止实例化
     */
    private Md5Utils() {}

    /**
     * 计算字符串的MD5值
     *
     * @param laintext 明文字符串
     * @return MD5值
     * @throws RuntimeException 加密失败时抛出
     */
    public static String MD5(String laintext) throws RuntimeException {
        if (laintext == null) {
            logger.warn("MD5参数为null");
            return null;
        }
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(laintext.getBytes("UTF-8"));
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (Exception e) {
            logger.error("Md5Utils.MD5加密失败", e);
            throw new RuntimeException("MD5 加密失败: " + e.getMessage(), e);
        }
        return re_md5;
    }
}
