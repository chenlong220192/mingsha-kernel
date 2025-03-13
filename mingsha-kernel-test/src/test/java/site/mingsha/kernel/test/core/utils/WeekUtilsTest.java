package site.mingsha.kernel.test.core.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import site.mingsha.kernel.core.utils.WeekUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ming Sha
 * @date 2025-03-12
 */
public class WeekUtilsTest {

    // ==================== 常量测试 ====================
    @Test
    void testConstants() {
        assertEquals(DayOfWeek.MONDAY, WeekUtils.MONDAY);
        assertEquals(DayOfWeek.SUNDAY.getValue(), 7);
    }

    // ==================== 星期名称测试 ====================
    @ParameterizedTest
    @MethodSource("weekdayNameProvider")
    void testGetWeekdayName(LocalDate date, TextStyle style, Locale locale, String expected) {
        assertEquals(expected, WeekUtils.getWeekdayName(date, style, locale));
    }

    static Stream<Arguments> weekdayNameProvider() {
        return Stream.of(
                // 格式样式测试
                Arguments.of(LocalDate.of(2023, 10, 23), TextStyle.FULL, Locale.CHINA, "星期一"),
                Arguments.of(LocalDate.of(2023, 10, 23), TextStyle.SHORT, Locale.CHINA, "周一"),
                Arguments.of(LocalDate.of(2023, 10, 23), TextStyle.FULL, Locale.US, "Monday"),
                Arguments.of(LocalDate.of(2023, 10, 23), TextStyle.SHORT, Locale.US, "Mon"),

                // 边界日期测试
                Arguments.of(LocalDate.of(2023, 12, 31), TextStyle.FULL, Locale.US, "Sunday"),
                Arguments.of(LocalDate.of(2024, 1, 1), TextStyle.FULL, Locale.CHINA, "星期一")
        );
    }

    // ==================== 快捷方法测试 ====================
    @Test
    void testChineseShortcuts() {
        LocalDate monday = LocalDate.of(2023, 10, 23);
        assertEquals("星期一", WeekUtils.getWeekdayName(monday));
        assertEquals("星期一", WeekUtils.getChineseFullWeekday());
    }

    @Test
    void testEnglishShortcuts() {
        LocalDate sunday = LocalDate.of(2023, 10, 29);
        assertEquals("Sunday", WeekUtils.getWeekdayName(sunday, TextStyle.FULL, Locale.US));
        assertEquals("Sun", WeekUtils.getEnglishShortWeekday());
    }

    // ==================== ISO星期值测试 ====================
    @ParameterizedTest
    @MethodSource("isoDayProvider")
    void testGetISODayValue(LocalDate date, int expected) {
        assertEquals(expected, WeekUtils.getISODayValue(date));
    }

    static Stream<Arguments> isoDayProvider() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 10, 23), 1),   // 周一
                Arguments.of(LocalDate.of(2023, 10, 24), 2),   // 周二
                Arguments.of(LocalDate.of(2023, 10, 29), 7)    // 周日
        );
    }

    // ==================== 工作日判断测试 ====================
    @ParameterizedTest
    @ValueSource(strings = {"2023-10-23", "2023-10-27"}) // 周一到周五
    void testWorkingDays(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        assertAll(
                () -> assertTrue(WeekUtils.isWorkingDay(date)),
                () -> assertFalse(WeekUtils.isWeekend(date))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023-10-28", "2023-10-29"}) // 周六、周日
    void testWeekends(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        assertAll(
                () -> assertTrue(WeekUtils.isWeekend(date)),
                () -> assertFalse(WeekUtils.isWorkingDay(date))
        );
    }

    // ==================== 异常处理测试 ====================
    @Test
    void testNullParameters() {
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> WeekUtils.getWeekdayName(null, TextStyle.FULL, Locale.CHINA)),

                () -> assertThrows(NullPointerException.class,
                        () -> WeekUtils.getISODayValue(null)),

                () -> assertThrows(NullPointerException.class,
                        () -> WeekUtils.isWeekend(null))
        );
    }

    // ==================== 当前日期测试 ====================
    @Test
    void testCurrentDateMethods() {
        assertDoesNotThrow(() -> {
            int isoValue = WeekUtils.getISODayValue();
            assertTrue(isoValue >= 1 && isoValue <= 7);

            String currentWeekday = WeekUtils.getWeekdayName();
            assertNotNull(currentWeekday);
        });
    }

    // ==================== 当前日期是否周末测试 ====================
    @Test
    void testCurrentDateIsWeekend() {
        assertTrue(WeekUtils.isWeekend());
    }

    // ==================== 给定日期是否周末测试 ====================
    @ParameterizedTest
    @ValueSource(
            strings = {
                    "2025-03-10", // 周一
                    "2025-03-11", // 周二
                    "2025-03-12", // 周三
                    "2025-03-13", // 周四
                    "2025-03-14", // 周五
                    "2025-03-15", // 周六
                    "2025-03-16" // 周日
            }
    )
    void testIsWeekendParameters(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
         assertTrue(WeekUtils.isWeekend(date));
    }

}
