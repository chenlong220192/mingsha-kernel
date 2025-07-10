# mingsha-kernel-logger

本模块提供统一日志工具，便于业务日志规范输出与链路追踪，支持MDC上下文、结构化日志、日志格式模板等。

## 主要API

### LogUtils
- 日志输出：
  - `debug/info/warn/error(String message)`：输出普通日志。
  - `debug/info/warn/error(MingshaLog log)`：结构化日志输出，支持链路追踪、模块、分类等。
  - `debug/info/warn/error(String filter1, String message)`：带自定义过滤字段日志。
- MDC上下文管理：
  - `setTraceId(String traceId)`：设置链路追踪ID。
  - `setModule(String module)`：设置模块标识。
  - `setTraceIdAndModule(String traceId, String module)`：同时设置traceId和module。
  - `removeTraceId/removeModule/removeFilter1/removeAll/clear`：移除/清空MDC上下文。
  - `getTraceId/getModule/getFilter1`：获取MDC上下文字段。
  - `setMDCContext(Map<String, String>)`、`getMDCContext()`：批量设置/获取MDC上下文。
- 日志格式化：
  - 内部`format`方法，支持多维度日志结构化，自动补全traceId、module、category（类名）、subCategory（方法名）、filter1、message。

### MingshaLog
- 日志数据结构，支持链式Builder模式：
  - 字段：`traceId`、`module`、`category`、`subCategory`、`filter1`、`message`。
  - 用法：
    ```java
    MingshaLog log = MingshaLog.builder()
        .traceId("xxx")
        .module("order")
        .category("OrderService")
        .subCategory("createOrder")
        .filter1("userId:123")
        .message("下单成功")
        .build();
    LogUtils.info(log);
    ```

### LogPattern
- 日志格式模板接口，内置多种INFO/WARN/ERROR格式常量，支持HTTP、无请求、无响应、无时间等多场景。
- 典型用法：
  ```java
  LogUtils.info(String.format(LogPattern.INFO_HTTP, 100, url, req, resp));
  ```

## 典型用法示例

```java
// 普通日志
LogUtils.info("服务启动成功");

// 结构化日志
MingshaLog log = MingshaLog.builder()
    .traceId("abc123")
    .module("user")
    .message("用户注册成功")
    .build();
LogUtils.info(log);

// 设置链路追踪
LogUtils.setTraceId("trace-001");
LogUtils.setModule("order");
LogUtils.info("下单流程开始");

// 获取当前traceId
String tid = LogUtils.getTraceId();
```

> 推荐结合MDC（Mapped Diagnostic Context）实现分布式链路追踪，详细API请查阅源码与注释。 