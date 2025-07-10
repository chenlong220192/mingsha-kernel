# mingsha-kernel-model

本模块定义通用请求与响应数据结构，便于服务间数据传递和标准化。

## 主要API

- `RequestDTO`
  - 字段：`traceId`（链路追踪ID）。
  - 工厂方法：`newInstance()`。
  - 支持链式设置traceId。

- `ResponseDTO<T>`
  - 字段：`success`（是否成功）、`code`（状态码）、`message`（响应信息）、`data`（响应数据）、`traceId`（链路追踪ID）。
  - 工厂方法：`success()`、`success(String message)`、`success(Object data)`、`success(String message, Object data)`、`success(String code, String message, Object data)`、`fail()`、`fail(String message)`、`fail(String code, String message)`。
  - 支持链式设置各字段。

> 适用于微服务、接口层通用数据结构。 