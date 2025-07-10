# mingsha-kernel-core

本模块为核心工具模块，包含线程池、ID生成、限流、线程上下文、HTTP工具、Bean映射、日期、正则、压缩、异常处理、集合分组、校验、IP工具、计时等核心能力。

## 主要API与工具类

### 线程池相关
- `MingshaThreadPool`：线程池工厂，支持多种参数自定义，底层MDC透传。
  - `getThreadPool(String poolName)`：按名称获取线程池。
  - `getThreadPool(int corePoolSize, int maximumPoolSize)`：自定义核心/最大线程数。
  - `getThreadPool()`：获取默认线程池。
  - `newThreadPool(...)`：多重重载，支持自定义拒绝策略、队列、存活时间等。
  - `newScheduledThreadPool(int corePoolSize)`：定时任务线程池。

### ID生成
- `UUIDGenerator`：生成唯一UUID字符串。
- `SnowflakeIdGenerator`：分布式雪花ID生成器，支持高并发唯一ID。

### 限流
- `MingshaRateLimiter`：令牌桶限流器，支持自定义容量、补充速率、间隔，`tryAcquire()`尝试获取令牌。

### 线程上下文
- `ThreadLocalUtils`：线程本地变量工具，支持上下文初始化、销毁、设置/获取key、获取全部Map。

### HTTP工具
- `OkHttpUtils`：基于OkHttp的HTTP请求工具，支持GET/POST/PUT/DELETE，支持自定义Header、Body，自动处理SSL。
  - `request(url, method, headers, body)`
  - `get(url, headers)`、`post(obj, url, headers)`等

### Bean映射
- `BeanMapperUtils`：JavaBean与Map互转，`bean2Map`、`map2Bean`。

### 日期时间
- `DateUtils`：日期格式化、解析、常用格式常量、时间戳处理等。

### 正则校验
- `RegexUtils`：常用正则表达式校验（邮箱、手机号、IP、日期、年龄、证件号、金额等）。

### 压缩解压
- `GzipUtils`：Gzip压缩与解压，`gZip`、`unGzip`。

### 异常处理
- `ExceptionUtils`：异常堆栈截取，`extract(Exception e)`。
- `BizException`：业务自定义异常，支持code/message。

### 集合分组
- `ListSplitUtils`：集合分组，支持按容量或批次数分组。

### 校验工具
- `ValidateUtils`：判空、非空、批量判空等。

### IP工具
- `IpUtils`：获取本地IP、IP:端口、IP格式处理。

### 计时工具
- `StopwatchUtils`：基于Guava的计时器，`getNewStopwatch`、`startStopWatch`、`stop`。

### 周工具
- `WeekUtils`：判断是否周末、工作日，获取星期常量。

## 典型用法示例

```java
// 获取线程池
ExecutorService pool = MingshaThreadPool.getThreadPool("myPool");

// 雪花ID生成
long id = SnowflakeIdGenerator.genSnowFlakeID();

// Bean转Map
Map<String, Object> map = BeanMapperUtils.bean2Map(bean);

// HTTP请求
String resp = OkHttpUtils.get("http://example.com");

// 校验
boolean empty = ValidateUtils.isEmpty(list);
```

> 详细API请查阅各工具类源码与注释。 