package site.mingsha.kernel.core.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Ming Sha
 * @create: 2020-06-11
 */
public class ThreadLocalUtils {

    /**
     *
     */
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<Map<String, Object>>();

    /**
     *
     * @param context
     */
    public static void init(Map<String, Object> context) {
        THREAD_LOCAL.set(context);
    }

    /**
     *
     */
    public static void destroy() {
        THREAD_LOCAL.remove();
    }

    /* ------------------------------------------------------------ */

    /**
     * 
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getContext(String key) {
        Map<String, Object> context = THREAD_LOCAL.get();
        return context != null && context.containsKey(key) ? (T) context.get(key) : null;
    }

    /**
     * 
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void setContext(String key, T value) {
        Map<String, Object> context = THREAD_LOCAL.get();
        if (Objects.isNull(context)) {
            context = new HashMap<>(1);
            init(context);
        }
        context.put(key, value);
    }

    /**
     *
     * @return
     */
    public static Map<String, Object> getContextMap() {
        return THREAD_LOCAL.get();
    }

}
