package site.mingsha.kernel.core.thread;

import java.util.concurrent.*;

/**
 * 线程池创建工具
 *
 * @author Ming Sha
 * @create: 2020-05-21 17:49
 */
public class MingshaThreadPool {

    /* ---------------------------- 拿来即用线程池 -------------------------------- */

    /**
     * 根据线程名称创建线程(注意:要根据实际每分钟的任务数来决定线程池大小)
     *
     * corePoolSize    线程池核心线程数 —— 缺省8
     * maximumPoolSize 线程池最大线程数 —— 缺省32
     * keepAliveTime   线程的最大空闲时间 —— 缺省60
     * unit            空闲时间的单位 —— 缺省SECONDS
     * workQueue       任务队列  —— 缺省ArrayBlockingQueue，容量32。
     * poolName        线程池名称
     *
     * @param poolName 线程池名称
     * @return
     */
    public static ExecutorService getThreadPool(String poolName) {
        return MdcThreadPoolExecutor.newWithInheritedMdc(
                8,
                32,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(32),
                MdcThreadPoolExecutor.EventThreadFactory.newInstance(poolName),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     *
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @return
     */
    public static ExecutorService getThreadPool(int corePoolSize, int maximumPoolSize) {
        return MdcThreadPoolExecutor.newWithInheritedMdc(
                corePoolSize,
                maximumPoolSize,
                300L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),
                MdcThreadPoolExecutor.EventThreadFactory.newInstance("default"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     *
     * @return
     */
    public static ExecutorService getThreadPool() {
        return MdcThreadPoolExecutor.newWithInheritedMdc(
                5,
                20,
                300L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),
                MdcThreadPoolExecutor.EventThreadFactory.newInstance("default"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /* ---------------------------- 自定义线程池 -------------------------------- */

    /**
     *
     * @param poolName 线程池名称
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 存活时间（TimeUnit.MILLISECONDS）
     * @param queueSize 任务队列容量
     * @return
     */
    public static ExecutorService newThreadPool(String poolName,
                                                int corePoolSize,
                                                int maximumPoolSize,
                                                long keepAliveTime,
                                                int queueSize) {
        return MdcThreadPoolExecutor.newWithInheritedMdc(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize),
                MdcThreadPoolExecutor.EventThreadFactory.newInstance(poolName),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 默认拒绝策略（AbortPolicy）
     *
     * @param poolName 线程池名称
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 存活时间
     * @param queueSize 任务队列容量
     * @return
     */
    public static ExecutorService newThreadPool(String poolName,
                                                int corePoolSize,
                                                int maximumPoolSize,
                                                long keepAliveTime,
                                                TimeUnit timeUnit,
                                                int queueSize) {
        return MdcThreadPoolExecutor.newWithInheritedMdc(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingQueue<>(queueSize),
                MdcThreadPoolExecutor.EventThreadFactory.newInstance(poolName)
        );
    }

    /**
     * 默认拒绝策略（AbortPolicy）
     *
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 存活时间
     * @param unit 存活时间单位
     * @param workQueue 任务队列
     * @param threadFactory 线程工厂
     * @return
     */
    public static ExecutorService newThreadPool(int corePoolSize,
                                                int maximumPoolSize,
                                                long keepAliveTime,
                                                TimeUnit unit,
                                                BlockingQueue<Runnable> workQueue,
                                                ThreadFactory threadFactory) {
        return MdcThreadPoolExecutor.newWithInheritedMdc(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory
        );
    }

    /**
     * all in
     *
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 存活时间
     * @param unit 存活时间单位
     * @param workQueue 任务队列
     * @param threadFactory 线程工厂
     * @param handler 拒绝策略
     * @return
     */
    public static ExecutorService newThreadPool(int corePoolSize,
                                                int maximumPoolSize,
                                                long keepAliveTime,
                                                TimeUnit unit,
                                                BlockingQueue<Runnable> workQueue,
                                                ThreadFactory threadFactory,
                                                RejectedExecutionHandler handler) {
        return MdcThreadPoolExecutor.newWithInheritedMdc(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
    }

    /**
     * 定时ScheduledExecutor
     * @param corePoolSize  核心线程数
     * @return
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }
}
