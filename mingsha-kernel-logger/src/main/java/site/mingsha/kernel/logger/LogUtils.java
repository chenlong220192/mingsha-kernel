package site.mingsha.kernel.logger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import site.mingsha.kernel.logger.model.MingshaLog;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 日志工具
 *
 * category 缺省值为类名
 * subCategory 缺省值为方法名
 *
 * @author mingsha
 * @date: 2025-07-10
 */
public class LogUtils {

    /**
     * logger
     */
    private static final Logger       logger  = LoggerFactory.getLogger("PROJECT");

    private static final String TRACEID = "traceId";
    private static final String MODULE  = "module";
    private static final String FILTER1 = "filter1";

    private static final String PATTERN = "[%s][%s][%s][%s][%s]%s";

    /**
     * 工具类构造方法私有化，防止实例化
     */
    private LogUtils() {}

    /**
     *
     * @param log
     */
    public static void debug(MingshaLog log) {
        logger.debug(format(log));
    }

    /**
     *
     * @param log
     */
    public static void info(MingshaLog log) {
        logger.info(format(log));
    }

    /**
     *
     * @param log
     */
    public static void warn(MingshaLog log) {
        logger.warn(format(log));
    }

    /**
     *
     * @param log
     */
    public static void error(MingshaLog log) {
        logger.error(format(log));
    }

    /**
     *
     * @param message
     */
    public static void debug(String message) {
        logger.debug(format(message));
    }

    /**
     *
     * @param message
     */
    public static void info(String message) {
        logger.info(format(message));
    }

    /**
     *
     * @param message
     */
    public static void warn(String message) {
        logger.warn(format(message));
    }

    /**
     *
     * @param message
     */
    public static void error(String message) {
        logger.error(format(message));
    }

    /**
     *
     * @param filter1
     * @param message
     */
    public static void debug(String filter1, String message) {
        logger.debug(format(filter1,message));
    }

    /**
     *
     * @param filter1
     * @param message
     */
    public static void info(String filter1, String message) {
        logger.info(format(filter1,message));
    }

    /**
     *
     * @param filter1
     * @param message
     */
    public static void warn(String filter1, String message) {
        logger.warn(format(filter1,message));
    }

    /**
     *
     * @param filter1
     * @param message
     */
    public static void error(String filter1, String message) {
        logger.error(format(filter1,message));
    }

    /* --------------------------------------------- */

    /**
     *
     * @param traceId
     */
    public static void setTraceId(String traceId) {
        removeTraceId();
        if (StringUtils.isNotEmpty(traceId)) {
            MDC.put(TRACEID, traceId);
        }
    }

    /**
     * 设置模块标志
     * 若未事先设置TraceId则自动生成UUID
     *
     * @param module
     */
    public static void setModule(String module) {
        //
        if(StringUtils.isBlank(MDC.get(TRACEID))){
            setTraceId(UUID.randomUUID().toString());
        }
        removeModule();
        if (StringUtils.isNotEmpty(module)) {
            MDC.put(MODULE, module);
        }
    }

    /**
     *
     * @param traceId
     * @param module
     */
    public static void setTraceIdAndModule(String traceId,String module) {
        setTraceId(traceId);
        setModule(module);
    }

    /**
     * removeTraceId
     */
    public static void removeTraceId() {
        MDC.remove(TRACEID);
    }

    /**
     * removeModule
     */
    public static void removeModule() {
        MDC.remove(MODULE);
    }

    /**
     * removeFilter1
     */
    public static void removeFilter1() {
        MDC.remove(FILTER1);
    }

    /**
     * remove traceId & module & filter1
     */
    public static void removeAll() {
        removeTraceId();
        removeModule();
        removeFilter1();
    }

    /**
     * clear MDC
     */
    public static void clear() {
        MDC.clear();
    }

    /**
     * traceId
     *
     * @return
     */
    public static String getTraceId() {
        return MDC.get(TRACEID);
    }

    /**
     * module
     *
     * @return
     */
    public static String getModule() {
        return MDC.get(MODULE);
    }

    /**
     * filter1
     *
     * @return
     */
    public static String getFilter1() {
        return MDC.get(FILTER1);
    }

    /* --------------------------------------------- */

    /**
     *
     * @param mdcContext
     */
    public static void  setMDCContext(Map<String, String> mdcContext){
        removeAll();
        if (Objects.nonNull(mdcContext)){
            MDC.setContextMap(mdcContext);
        }
    }

    /**
     *
     * @return
     */
    public static Map<String, String> getMDCContext(){
        return MDC.getCopyOfContextMap();
    }

    /* --------------------------------------------- */

    /**
     *
     * @param log
     * @return
     */
    private static String format(final MingshaLog log) {
        return format(
                exchange(MDC.get(TRACEID),log.getTraceId()),
                exchange(MDC.get(MODULE),log.getModule()),
                exchange(category(),log.getCategory()),
                exchange(subCategory(),log.getSubCategory()),
                exchange(MDC.get(FILTER1),log.getFilter1()),
                log.getMessage()
        );
    }

    /**
     *
     * @param filter1
     * @param message
     * @return
     */
    private static String format(final String filter1, final String message){
        return format(
                MDC.get(TRACEID),
                MDC.get(MODULE),
                category(),
                subCategory(),
                filter1,
                message
        );
    }

    /**
     *
     * @param message
     * @return
     */
    private static String format(final String message){
        return format(
                MDC.get(TRACEID),
                MDC.get(MODULE),
                category(),
                subCategory(),
                MDC.get(FILTER1),
                message
        );
    }

    /**
     *
     * @param traceId
     * @param module
     * @param category
     * @param subCategory
     * @param filter1
     * @param message
     * @return
     */
    private static String format(final String traceId,
                                 final String module,
                                 final String category,
                                 final String subCategory,
                                 final String filter1,
                                 final String message
                                 ){
        return String.format(PATTERN,traceId,module,category,subCategory,filter1,message);
    }

    /**
     *
     * @param mdcValue
     * @param value
     * @return
     */
    private static String exchange(String mdcValue, String value){
        return StringUtils.isEmpty(value) ? mdcValue : value;
    }

    /**
     * extract category
     *
     * @return
     */
    private static String category(){
        return Thread.currentThread().getStackTrace()[4].getFileName().split("\\.")[0];
    }

    /**
     * extract subCategory
     *
     * @return
     */
    private static String subCategory(){
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

}
