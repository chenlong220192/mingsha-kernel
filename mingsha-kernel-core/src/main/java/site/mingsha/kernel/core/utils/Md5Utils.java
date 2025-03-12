/**
 * Creation Date:2016年1月19日-上午11:13:36
 * 
 * Copyright 2008-2016 © 同程网 Inc. All Rights Reserved
 */
package site.mingsha.kernel.core.utils;

import java.security.MessageDigest;

/**
 * @author Ming Sha
 * @create: 2020-05-20 16:14
 */
public class Md5Utils {

    /**
     * 
     * 
     * @param laintext
     * @return
     * @throws RuntimeException
     */
    public static String MD5(String laintext) throws RuntimeException {
        String re_md5 = new String();
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
            throw new RuntimeException("MD5 加密失败");
        }
        return re_md5;
    }
}
