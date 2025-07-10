package site.mingsha.kernel.core.utils;

import java.lang.management.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * JVM运行时信息工具类，支持内存、线程、GC、系统属性等查询。
 * @author mingsha
 * @date: 2025-07-10
 */
public class JvmUtils {
    private JvmUtils() {}

    /**
     * 获取JVM最大可用内存（字节）
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * 获取JVM已分配内存（字节）
     */
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * 获取JVM空闲内存（字节）
     */
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * 获取JVM可用处理器核数
     */
    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取当前线程数
     */
    public static int getThreadCount() {
        return ManagementFactory.getThreadMXBean().getThreadCount();
    }

    /**
     * 获取所有线程信息
     */
    public static ThreadInfo[] getAllThreadInfo() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.dumpAllThreads(false, false);
    }

    /**
     * 获取GC统计信息
     */
    public static List<String> getGcStats() {
        return ManagementFactory.getGarbageCollectorMXBeans().stream()
                .map(gc -> gc.getName() + ": count=" + gc.getCollectionCount() + ", time=" + gc.getCollectionTime() + "ms")
                .collect(Collectors.toList());
    }

    /**
     * 获取JVM启动参数
     */
    public static List<String> getInputArguments() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments();
    }

    /**
     * 获取JVM系统属性
     */
    public static Properties getSystemProperties() {
        return System.getProperties();
    }

    /**
     * 获取JVM环境变量
     */
    public static Map<String, String> getEnv() {
        return System.getenv();
    }

    /**
     * 获取JVM名称（如HotSpot）
     */
    public static String getJvmName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    /**
     * 获取JVM版本
     */
    public static String getJvmVersion() {
        return ManagementFactory.getRuntimeMXBean().getVmVersion();
    }

    /**
     * 获取JVM启动时间（毫秒）
     */
    public static long getStartTime() {
        return ManagementFactory.getRuntimeMXBean().getStartTime();
    }

    /**
     * 获取JVM运行时长（毫秒）
     */
    public static long getUptime() {
        return ManagementFactory.getRuntimeMXBean().getUptime();
    }
}
