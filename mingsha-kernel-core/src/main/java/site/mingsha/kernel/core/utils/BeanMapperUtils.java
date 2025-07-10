package site.mingsha.kernel.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean转换工具
 *
 * @author mingsha
 * @date: 2025-07-10
 */
public class BeanMapperUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanMapperUtils.class);

    /**
     * 工具类构造方法私有化，防止实例化
     */
    private BeanMapperUtils() {}

    /**
     * 将JavaBean转换为Map
     *
     * @param bean JavaBean对象
     * @return 属性Map，bean为null时返回空Map
     */
    public static Map<String, Object> bean2Map(Object bean) {
        Map<String, Object> properties = Maps.newHashMap();
        if (bean == null) {
            logger.warn("bean2Map参数为null");
            return properties;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : propertyDescriptors) {
                String beanName = pd.getName();
                if (!StringUtils.equals(beanName, "class")) {
                    Method getter = pd.getReadMethod();
                    Object beanValue = getter.invoke(bean);
                    properties.put(beanName, beanValue);
                }
            }
            return properties;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
            logger.error("BeanMapperUtils.bean2Map异常", e);
            throw new RuntimeException("bean2Map异常: " + e.getMessage(), e);
        }
    }

    /**
     * 将Map转换为JavaBean
     *
     * @param properties 属性Map
     * @param clazz 目标类
     * @param <T> 泛型
     * @return JavaBean对象，properties为null时返回null
     */
    public static <T> T map2Bean(Map<String, Object> properties, Class<T> clazz) {
        if (properties == null || clazz == null) {
            logger.warn("map2Bean参数为null");
            return null;
        }
        try {
            T bean = clazz.newInstance();
            // Spring BeanUtils不支持Map->Bean，需手动set属性
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : propertyDescriptors) {
                String propName = pd.getName();
                if (properties.containsKey(propName)) {
                    Method setter = pd.getWriteMethod();
                    if (setter != null) {
                        setter.invoke(bean, properties.get(propName));
                    }
                }
            }
            return bean;
        } catch (IntrospectionException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("BeanMapperUtils.map2Bean异常", e);
            throw new RuntimeException("map2Bean异常: " + e.getMessage(), e);
        }
    }
}
