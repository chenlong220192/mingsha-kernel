package site.mingsha.kernel.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

/**
 * Bean转换工具
 *
 * @author Ming Sha
 * @create: 2020-05-20 15:50
 */
public class BeanMapperUtils {

    /**
     * 
     */
    private BeanMapperUtils() {
    }

    /**
     * 
     * @param bean
     * @return
     */
    public static Map<String, Object> bean2Map(Object bean) {
        Map<String, Object> properties = Maps.newHashMap();
        if (bean == null) {
            return properties;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var7 = propertyDescriptors;
            int var6 = propertyDescriptors.length;

            for (int var5 = 0; var5 < var6; ++var5) {
                PropertyDescriptor pd = var7[var5];
                String beanName = pd.getName();
                if (!StringUtils.equals(beanName, "class")) {
                    Method getter = pd.getReadMethod();
                    Object beanValue = getter.invoke(bean);
                    properties.put(beanName, beanValue);
                }
            }

            return properties;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException var11) {
            throw new RuntimeException(var11);
        }
    }

    /**
     * 
     * @param properties
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> properties, Class<T> clazz) {
        if (properties == null) {
            return null;
        }
        try {
            T bean = clazz.newInstance();
            BeanUtils.populate(bean, properties);
            return bean;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException var3) {
            throw new RuntimeException(var3);
        }
    }
}
