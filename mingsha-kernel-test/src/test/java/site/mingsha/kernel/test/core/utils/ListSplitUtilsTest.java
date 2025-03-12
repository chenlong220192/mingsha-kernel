package site.mingsha.kernel.test.core.utils;

import org.junit.jupiter.api.Test;
import site.mingsha.kernel.core.utils.ListSplitUtils;
import com.google.common.collect.Lists;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ming Sha
 * @date 2020-06-17
 */
public class ListSplitUtilsTest {

    private static final List<String> list = Lists.newArrayList("1", "2", "2", "2", "2", "2", "2", "2", "2", "2");

    @Test
    public void splitWithPart() {
        List<List<String>> result = ListSplitUtils.splitWithPart(list, 3);
        assertEquals(result.size(), 3);
    }

    @Test
    public void splitByCondition() {
        List<List<String>> result = ListSplitUtils.splitByCondition(list, 3);
        assertEquals(result.size(), 4);
    }

}
