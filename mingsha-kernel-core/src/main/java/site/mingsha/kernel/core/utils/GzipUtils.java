package site.mingsha.kernel.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gzip 压缩，解压
 * 
 * @author mingsha
 * @date: 2025-07-10
 */
public class GzipUtils {

    private static final Logger logger = LoggerFactory.getLogger(GzipUtils.class);

    /**
     * 工具类构造方法私有化，防止实例化
     */
    private GzipUtils() {}

    /**
     * 压缩
     *
     * @param data 待压缩字节数组
     * @return 压缩后字节数组，异常时返回null
     */
    public static byte[] gZip(byte[] data) {
        if (data == null) {
            logger.warn("gZip参数为null");
            return null;
        }
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            logger.error("GzipUtils.gZip压缩异常", ex);
        }
        return b;
    }

    /**
     * 解压
     *
     * @param data 待解压字节数组
     * @return 解压后字节数组，异常时返回null
     */
    public static byte[] unGZip(byte[] data) {
        if (data == null) {
            logger.warn("unGZip参数为null");
            return null;
        }
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            logger.error("GzipUtils.unGZip解压异常", ex);
        }
        return b;
    }

}
