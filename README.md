# Mingsha Kernel

高效、通用的 Java 基础工具包，涵盖常量、核心工具、日志、通用模型、测试等模块，助力企业级应用开发。

---

## 📦 模块结构

| 模块 | 说明 | 文档 |
|------|------|------|
| mingsha-kernel-constants | 全局常量定义，统一维护项目常量 | [查看](./mingsha-kernel-constants/README.md) |
| mingsha-kernel-core      | 核心工具类，含线程池、ID生成、限流、HTTP、Bean映射、日期、正则、压缩、异常、集合分组、校验、IP、计时等 | [查看](./mingsha-kernel-core/README.md) |
| mingsha-kernel-logger    | 日志工具，支持结构化日志、MDC链路追踪、日志格式模板 | [查看](./mingsha-kernel-logger/README.md) |
| mingsha-kernel-model     | 通用请求/响应数据结构，适用于微服务/接口层 | [查看](./mingsha-kernel-model/README.md) |
| mingsha-kernel-test      | 单元测试与集成测试用例 | [查看](./mingsha-kernel-test/README.md) |
| mingsha-kernel-all       | 聚合所有模块，便于统一依赖 | - |

---

## 🚀 快速开始

### 环境要求
- JDK 1.8 及以上
- Maven 3.6+

### 一键构建
```sh
make package SKIP_TEST=true
```

### 常用命令

| 命令                | 说明                         |
|---------------------|------------------------------|
| make help           | 查看所有可用命令及说明       |
| make clean          | 清理所有模块的构建产物        |
| make test           | 执行所有单元测试              |
| make package        | 打包所有模块                  |
| make install        | 安装到本地仓库                |
| make deploy         | 部署到远程仓库                |
| make set-version    | 设置项目版本号                |

> 说明：可通过 `SKIP_TEST=true` 跳过测试，`NEW_VERSION=xxxx` 指定新版本号。

### 设置项目版本号
```sh
make set-version NEW_VERSION=2025.07.10.01
```

---

## 🤝 贡献指南

1. Fork 本仓库，创建新分支进行开发。
2. 保持代码风格统一，补全注释。
3. 提交前请确保通过所有单元测试。
4. 提交 PR 时请详细描述变更内容。

---

## ⚠️ 注意事项
- `-SNAPSHOT` 为快照版本，开发中使用，发布前请移除。
- 部署到私服需在 `settings.xml` 配置账号密码，`server.id` 与 `pom.xml` 保持一致。
- 强制拉取最新快照：`mvn clean package -U`

---

## 📬 联系方式
如有问题或建议，欢迎提 Issue 或 PR。

## 许可证
本项目采用 MIT License，详见 [LICENSE](./LICENSE)
