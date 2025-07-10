package site.mingsha.kernel.core.filter;

import java.util.BitSet;
import java.util.List;
import java.util.Objects;

/**
 * @author mingsha
 * @date: 2025-07-10
 *
 * 通用布隆过滤器实现，支持泛型元素。
 */
public class MingshaBloomFilter<T> {
    private final int bitSize;
    private final int hashCount;
    private final BitSet bitSet;

    /**
     * 构造布隆过滤器
     * @param bitSize 位图大小（建议为2的幂）
     * @param hashCount 哈希函数数量
     */
    public MingshaBloomFilter(int bitSize, int hashCount) {
        this.bitSize = bitSize;
        this.hashCount = hashCount;
        this.bitSet = new BitSet(bitSize);
    }

    /**
     * 添加元素
     */
    public void add(T value) {
        for (int i = 0; i < hashCount; i++) {
            int hash = hash(value, i);
            bitSet.set(Math.abs(hash % bitSize));
        }
    }

    /**
     * 批量添加
     */
    public void addAll(List<T> values) {
        if (values != null) {
            for (T v : values) {
                add(v);
            }
        }
    }

    /**
     * 判断元素是否可能存在
     */
    public boolean mightContain(T value) {
        for (int i = 0; i < hashCount; i++) {
            int hash = hash(value, i);
            if (!bitSet.get(Math.abs(hash % bitSize))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 简单哈希函数（可根据需要扩展）
     */
    private int hash(T value, int seed) {
        int h = Objects.hashCode(value);
        h ^= (seed * 0x5bd1e995);
        h ^= (h >>> 16);
        return h;
    }

    /**
     * 清空过滤器
     */
    public void clear() {
        bitSet.clear();
    }

    /**
     * 获取位图大小
     */
    public int getBitSize() {
        return bitSize;
    }

    /**
     * 获取哈希函数数量
     */
    public int getHashCount() {
        return hashCount;
    }
}
