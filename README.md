## 核心工具包

- mingsha-kernel-all ALL-IN
- mingsha-kernel-constants 公共常量
- mingsha-kernel-core 核心
- mingsha-kernel-logger 日志
- mingsha-kernel-model 模型
- mingsha-kernel-test 单元测试

***

⚠️注意：
- `-SNAPSHOT`为快照版本，在开发过程中使用。确认无误后需要移除该后缀，并重新部署至`release`仓库。
- 部署jar到远程仓库：`sh deploy.sh` or `mvn deploy`
- 推送私服需要在`setting.xml`中配置账号密码，`server.id`需要与`pom.xml`中`repository.id`相对应。
- 强制拉取最新快照`mvn clean package -U`
