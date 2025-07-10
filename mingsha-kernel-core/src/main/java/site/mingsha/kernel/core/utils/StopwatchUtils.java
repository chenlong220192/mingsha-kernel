package site.mingsha.kernel.core.utils;


import com.google.common.base.Stopwatch;

/**
 * @author mingsha
 * @date: 2025-07-10
 */
public class StopwatchUtils {

    /**
     * 生成一个新的计时器实例
     *
     * @return
     */
    public static Stopwatch getNewStopwatch() {
        return Stopwatch.createUnstarted();
    }

    /**
     * 启动定时器
     *
     * @param stopwatch
     */
    public static void startStopWatch(Stopwatch stopwatch) {
        if (stopwatch.isRunning()) {
            stopwatch.stop();
        }
        stopwatch.start();
    }

    /**
     * 停止定时器
     *
     * @param stopwatch
     */
    public static void stop(Stopwatch stopwatch) {
        if (stopwatch.isRunning()) {
            stopwatch.stop();
        }
    }

}
