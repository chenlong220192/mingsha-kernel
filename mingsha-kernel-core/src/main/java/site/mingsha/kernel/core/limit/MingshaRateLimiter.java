package site.mingsha.kernel.core.limit;

 import java.util.concurrent.atomic.AtomicLong;

 /**
  * @author Ming Sha
  * @create: 2020-05-25 23:19
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
      * 尝试获取令牌。
      * @return 如果成功获取令牌返回 true，否则返回 false。
      */
     public synchronized boolean tryAcquire() {
         refillTokensIfNeeded();
         if (availableTokens.get() > 0) {
             availableTokens.decrementAndGet();
             return true;
         }
         return false;
     }

     /**
      * 补充令牌（如果需要）。
      */
     private void refillTokensIfNeeded() {
         long now = System.currentTimeMillis();
         long lastRefill = lastRefillTime.get();
         if (now - lastRefill >= refillIntervalMillis) {
             long elapsedIntervals = (now - lastRefill) / refillIntervalMillis;
             long tokensToAdd = elapsedIntervals * refillTokens;
             availableTokens.set(Math.min(capacity, availableTokens.get() + tokensToAdd));
             lastRefillTime.set(lastRefill + elapsedIntervals * refillIntervalMillis);
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
