package site.mingsha.kernel.core.limit;

 import java.util.concurrent.atomic.AtomicLong;

 /**
  * @author mingsha
  * @date: 2025-07-10
  */
 public class MingshaRateLimiter {

     private final long capacity; // 桶的容量，即最大允许的令牌数
     private final long refillTokens; // 每次补充的令牌数
     private final long refillIntervalMillis; // 补充令牌的时间间隔（毫秒）

     private final AtomicLong availableTokens; // 当前可用的令牌数
     private final AtomicLong lastRefillTime; // 上次补充令牌的时间戳

     public MingshaRateLimiter(long capacity, long refillTokens, long refillIntervalMillis) {
         if (capacity <= 0 || refillTokens <= 0 || refillIntervalMillis <= 0) {
             throw new IllegalArgumentException("All parameters must be greater than 0");
         }
         this.capacity = capacity;
         this.refillTokens = refillTokens;
         this.refillIntervalMillis = refillIntervalMillis;
         this.availableTokens = new AtomicLong(capacity);
         this.lastRefillTime = new AtomicLong(System.currentTimeMillis());
     }

     /**
      * 尝试获取令牌（高并发无锁版）。
      * @return 如果成功获取令牌返回 true，否则返回 false。
      */
     public boolean tryAcquire() {
         refillTokensIfNeeded();
         while (true) {
             long current = availableTokens.get();
             if (current <= 0) {
                 return false;
             }
             if (availableTokens.compareAndSet(current, current - 1)) {
                 return true;
             }
         }
     }

     /**
      * 补充令牌（高并发下防止重复补充）。
      */
     private void refillTokensIfNeeded() {
         long now = System.currentTimeMillis();
         while (true) {
             long lastRefill = lastRefillTime.get();
             if (now - lastRefill < refillIntervalMillis) {
                 return;
             }
             long elapsedIntervals = (now - lastRefill) / refillIntervalMillis;
             long tokensToAdd = elapsedIntervals * refillTokens;
             long currentTokens = availableTokens.get();
             long newTokens = Math.min(capacity, currentTokens + tokensToAdd);
             if (availableTokens.compareAndSet(currentTokens, newTokens)) {
                 long newRefillTime = lastRefill + elapsedIntervals * refillIntervalMillis;
                 lastRefillTime.compareAndSet(lastRefill, newRefillTime);
                 return;
             }
             // 有并发冲突，重试
         }
     }

     /**
      * 获取当前可用令牌数。
      * @return 当前可用令牌数。
      */
     public long getAvailableTokens() {
         refillTokensIfNeeded();
         return availableTokens.get();
     }
 }
