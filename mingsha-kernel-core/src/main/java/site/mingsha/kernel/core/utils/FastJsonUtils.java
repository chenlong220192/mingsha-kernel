package site.mingsha.kernel.core.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.TypeReference;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mingsha
 * @date: 2025-07-10
 */
public class FastJsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(FastJsonUtils.class);

    /**
     * 工具类构造方法私有化，防止实例化
     */
    private FastJsonUtils() {}

    /**
     * 用fastjson 将json字符串解析为一个 JavaBean
     *
     * @param jsonString json字符串
     * @param cls 目标类
     * @param <T> 泛型
     * @return 解析后的对象，失败返回null
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        if (jsonString == null || cls == null) {
            logger.warn("getObject参数为null");
            return null;
        }
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            logger.error("FastJsonUtils.getObject解析异常, jsonString={}, class={}", jsonString, cls.getName(), e);
        }
        return t;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     *
     * @param jsonString json字符串
     * @param cls 目标类
     * @param <T> 泛型
     * @return 解析后的List，失败返回空List
     */
    public static <T> List<T> getList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        if (jsonString == null || cls == null) {
            logger.warn("getList参数为null");
            return list;
        }
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            logger.error("FastJsonUtils.getList解析异常, jsonString={}, class={}", jsonString, cls.getName(), e);
        }
        return list;
    }

    /**
     * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
     *
     * @param jsonString json字符串
     * @return 解析后的List，失败返回空List
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (jsonString == null) {
            logger.warn("getListMap参数为null");
            return list;
        }
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            logger.error("FastJsonUtils.getListMap解析异常, jsonString={}", jsonString, e);
        }
        return list;
    }

    /**
     * 用fastjson 将jsonString 解析成 Map<String,Object>
     *
     * @param jsonString json字符串
     * @return 解析后的Map，失败返回空Map
     */
    public static Map<String, Object> getMap(String jsonString) {
        Map<String, Object> map = new HashMap<>();
        if (jsonString == null) {
            logger.warn("getMap参数为null");
            return map;
        }
        try {
            map = (Map<String, Object>) JSON.parse(jsonString);
        } catch (Exception e) {
            logger.error("FastJsonUtils.getMap解析异常, jsonString={}", jsonString, e);
        }
        return map;
    }

    /**
     * 将字符串转换为按顺序输出的key/value对象
     *
     * @param jsonString 要转换的JSON字符串
     * @return 按顺序存储键值对的JSONObject
     */
    public static JSONObject parseOrderedJson(String jsonString) {
        if (jsonString == null) {
            logger.warn("parseOrderedJson参数为null");
            return null;
        }
        try {
            // 使用LinkedHashMap来确保顺序
            Map<String, Object> orderedMap = JSONObject.parseObject(jsonString, LinkedHashMap.class, JSONReader.Feature.FieldBased);

            // 将LinkedHashMap转换为JSONObject并返回
            return new JSONObject(orderedMap);
        } catch (Exception e) {
            logger.error("FastJsonUtils.parseOrderedJson解析异常, jsonString={}", jsonString, e);
        }
        return null;
    }

    /**
     * 用fastjson 将obj解析成 json字符串
     *
     * @param object
     * @return
     */
    public static String getJsonStr(Object object) {
        if (object == null) {
            logger.warn("getJsonStr参数为null");
            return null;
        }
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            logger.error("FastJsonUtils.getJsonStr解析异常, object={}", object, e);
        }
        return null;
    }

}
