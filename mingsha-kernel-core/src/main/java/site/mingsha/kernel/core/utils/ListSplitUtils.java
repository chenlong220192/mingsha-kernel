package site.mingsha.kernel.core.utils;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * @author mingsha
 * @date: 2025-07-10
 */
public class ListSplitUtils {

    /**
     * 集合分组（指定每个批次的容量）
     *
     * @param list 待处理集合
     * @param limit 批次容量
     * @return
     */
    public static <T> List<List<T>> splitByCondition(List<T> list, int limit) {
        List<List<T>> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list) || limit <= 0) {
            return result;
        }
        int size = list.size();
        for (int i = 0; i < size; i += limit) {
            int end = Math.min(i + limit, size);
            result.add(list.subList(i, end));
        }
        return result;
    }

    /**
     * 集合分组（指定总批次，均匀分配余数，避免空分组）
     *
     * @param list 待处理集合
     * @param part 批次数
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitWithPart(List<T> list, int part) {
        List<List<T>> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list) || part <= 0) {
            return result;
        }
        int size = list.size();
        int min = size / part;
        int rem = size % part;
        int offset = 0;
        for (int i = 0; i < part; i++) {
            int start = offset;
            int end = start + min + (i < rem ? 1 : 0);
            if (start >= size) break;
            result.add(list.subList(start, Math.min(end, size)));
            offset = end;
        }
        return result;
    }

}
