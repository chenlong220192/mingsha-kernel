package site.mingsha.kernel.test.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import site.mingsha.kernel.core.filter.MingshaBloomFilter;

import java.util.Arrays;
import java.util.List;

/**
 * MingshaBloomFilter 单元测试
 * @author mingsha
 * @date: 2025-07-10
 */
public class MingshaBloomFilterTest {

    @Test
    public void testBasic() {
        MingshaBloomFilter<String> filter = new MingshaBloomFilter<>(1024, 3);
        filter.add("foo");
        filter.add("bar");
        Assertions.assertTrue(filter.mightContain("foo"));
        Assertions.assertTrue(filter.mightContain("bar"));
        Assertions.assertFalse(filter.mightContain("baz"));
    }

    @Test
    public void testBatchAdd() {
        MingshaBloomFilter<Integer> filter = new MingshaBloomFilter<>(2048, 4);
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        filter.addAll(nums);
        for (int n : nums) {
            Assertions.assertTrue(filter.mightContain(n));
        }
        Assertions.assertFalse(filter.mightContain(100));
    }

    @Test
    public void testClear() {
        MingshaBloomFilter<String> filter = new MingshaBloomFilter<>(512, 2);
        filter.add("hello");
        Assertions.assertTrue(filter.mightContain("hello"));
        filter.clear();
        Assertions.assertFalse(filter.mightContain("hello"));
    }
} 