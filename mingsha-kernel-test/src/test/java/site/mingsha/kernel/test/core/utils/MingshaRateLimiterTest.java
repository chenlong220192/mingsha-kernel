package site.mingsha.kernel.test.core.utils;

import org.junit.jupiter.api.Test;
import site.mingsha.kernel.core.limit.MingshaRateLimiter;

import java.util.List;

/**
 * @author Ming Sha
 * @date 2024-12-03
 */
public class MingshaRateLimiterTest {

  @Test
  public void test() {
      // 创建一个容量为 10，每秒补充 5 个令牌的限流器
      MingshaRateLimiter rateLimiter = new MingshaRateLimiter(10, 5, 1000);

      // 模拟请求
      for (int i = 0; i < 20; i++) {
          if (rateLimiter.tryAcquire()) {
              System.out.println("请求成功: " + i);
          } else {
              System.out.println("请求被限制: " + i);
          }

          // 模拟请求间隔
          try {
              Thread.sleep(200);
          } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
          }
      }
  }
}
