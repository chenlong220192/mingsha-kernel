package site.mingsha.kernel.core.thread;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import site.mingsha.kernel.core.utils.ExceptionUtils;
import site.mingsha.kernel.logger.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author Ming Sha
 * @create: 2020-05-21 18:20
 */
public class MdcThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     *
     */
    final private boolean                     useFixedContext;

    /**
     *
     */
    final private Map<String, String>         fixedContext;

    /**
     * 任务开始执行的时间
     * 当任务结束时，用任务结束时间减去开始时间计算任务执行时间。
     */
    private ConcurrentHashMap<String, Date>   startTimes;

    /**
     * 线程池名称
     */
    private ConcurrentHashMap<String, String> poolNames;

    /* ------------------------------------------------------------ */

    /**
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @return
     */
    public static MdcThreadPoolExecutor newWithInheritedMdc(int corePoolSize,
                                                            int maximumPoolSize,
                                                            long keepAliveTime,
                                                            TimeUnit unit,
                                                            BlockingQueue<Runnable> workQueue,
                                                            ThreadFactory threadFactory) {
        return new MdcThreadPoolExecutor(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /**
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param handler
     * @return
     */
    public static MdcThreadPoolExecutor newWithInheritedMdc(int corePoolSize,
                                                            int maximumPoolSize,
                                                            long keepAliveTime,
                                                            TimeUnit unit,
                                                            BlockingQueue<Runnable> workQueue,
                                                            ThreadFactory threadFactory,
                                                            RejectedExecutionHandler handler) {
        return new MdcThreadPoolExecutor(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /* ------------------------------------------------------------ */

    /**
     *
     * @param fixedContext
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     */
    private MdcThreadPoolExecutor(Map<String, String> fixedContext,
                                  int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.startTimes = new ConcurrentHashMap<>();
        this.poolNames = new ConcurrentHashMap<>();
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }

    /**
     *
     * @param fixedContext
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param handler
     */
    private MdcThreadPoolExecutor(Map<String, String> fixedContext,
                                  int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.startTimes = new ConcurrentHashMap<>();
        this.poolNames = new ConcurrentHashMap<>();
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }

    /* ------------------------------------------------------------ */

    /**
     * 任务执行之前，记录任务开始时间。
     *
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String key = String.valueOf(r.hashCode());
        startTimes.put(key, new Date());
        poolNames.put(key, t.getName());
    }

    /**
     * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code submit()} etc.)
     * all delegate to this method.
     *
     * @param command
     */
    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, getTargetMDCContext()));
    }

    /**
     * 任务执行后，增加对线程的监控，常规日志记载、异常记载。
     *
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        String key = String.valueOf(r.hashCode());
        Date startDate = startTimes.remove(key);
        Date finishDate = new Date();
        long diff = finishDate.getTime() - startDate.getTime();
        String poolName = StringUtils.trimToEmpty(poolNames.remove(key));
        // 统计任务耗时、初始线程数、正在执行的任务数量、已完成任务数量、任务总数、队列里缓存的任务数量、池中存在的最大线程数、核心线程数、最大允许的线程数、线程空闲时间、线程池是否关闭、线程池是否终止。
        LogUtils.debug(
                "pool status",
                String.format(
                        String.format(
                                "%s%s%s%s",
                                poolName,
                                "-monitor: Duration: %d ms, PoolSize: %d, Active: %d, Task Completed: %d, Task Count: %d, ",
                                "Queue(Pending tasks): %d, LargestPoolSize: %d, CorePoolSize: %d, MaximumPoolSize: %d,  ",
                                "KeepAliveTime: %d, isShutdown: %b, isTerminated: %b"
                        ),
                        diff,
                        this.getPoolSize(),
                        this.getActiveCount(),
                        this.getCompletedTaskCount(),
                        this.getTaskCount(),
                        this.getQueue().size(),
                        this.getLargestPoolSize(),
                        this.getCorePoolSize(),
                        this.getMaximumPoolSize(),
                        this.getKeepAliveTime(TimeUnit.MILLISECONDS),
                        this.isShutdown(),
                        this.isTerminated()
                )
        );
        if (t != null) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                t.printStackTrace(new PrintStream(baos));
                LogUtils.error("pool error", String.format("%s%s:%s","线程异常" , poolName , baos.toString()));
            } catch (Exception e) {
                LogUtils.error("pool error", String.format("%s%s:%s","线程异常" , poolName , ExceptionUtils.extract(e)));
            }
        }
    }

    /* ------------------------------------------------------------ */

    /**
     * Runnable包装
     *
     * @param runnable
     * @param context MDC上下文
     * @return
     */
    private static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return new Runnable() {
            @Override
            public void run() {
                Map previous = MDC.getCopyOfContextMap();
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                try {
                    runnable.run();
                } finally {
                    if (previous == null) {
                        MDC.clear();
                    } else {
                        MDC.setContextMap(previous);
                    }
                }
            }
        };
    }

    /**
     * 获取目标MDC上下文
     *
     * @return
     */
    private Map<String, String> getTargetMDCContext() {
        return useFixedContext ? fixedContext : MDC.getCopyOfContextMap();
    }

    /* ------------------------------------------------------------ */

    /**
     * 生成线程池所用的线程，只是改写了线程池默认的线程工厂，传入线程池名称，便于问题追踪
     *
     * @author Ming Sha
     * @create: 2020-06-11
     */
    public static class EventThreadFactory implements ThreadFactory {

        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        /**
         *
         * @param poolName
         * @return
         */
        public static EventThreadFactory newInstance(String poolName){
            return new EventThreadFactory(poolName);
        }

        /**
         * 初始化线程工厂
         *
         * @param poolName
         *            线程池名称
         */
        private EventThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = poolName + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        /**
         *
         * @param r
         * @return
         */
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
