# NXP 巡检系统（设备报修 / 巡检 / PM 管理）

基于 RuoYi 框架的设备巡检与报修管理系统（Java / Spring Boot），包含 Web 管理后台与 App API 接口。

## 模块结构

| 模块 | 说明 |
|---|---|
| ruoyi-admin | Web 后台 + App API 启动模块（打包为 ruoyi-admin.jar） |
| ruoyi-system | 业务逻辑（巡检、报修、设备、PM、模具等） |
| ruoyi-quartz | 定时任务模块 |
| ruoyi-common / ruoyi-framework | 通用组件与框架层 |

## 服务看门狗脚本（ruoyi-watchdog.bat）

Windows 部署机的服务守护脚本，提供**双层守护**，保证两个后端服务（设备端 8686 / 模具端 8585）7x24 运行：

- **内层 - 窗口守护**：双击脚本后为每个服务开一个最小化守护窗口，java 进程退出后 `RESTART_DELAY`（10 秒）内自动重启
- **外层 - 计划任务兜底**：`install` 后注册 Windows 计划任务（SYSTEM 账户），每 `SCAN_MINUTES`（1 分钟）运行一次 `task 服务号` 巡查，防止守护窗口被关闭、脚本被误杀

### 用法

```text
双击 ruoyi-watchdog.bat        启动全部服务，各开一个最小化守护窗口
ruoyi-watchdog.bat stop        停止全部服务（写停止标记，看门狗自动退出）
ruoyi-watchdog.bat status      查看服务 / 守护 / 计划任务 / 熔断状态
ruoyi-watchdog.bat install     安装看门狗计划任务（需管理员权限，自动弹 UAC）
ruoyi-watchdog.bat uninstall   卸载计划任务
```

### 可靠性设计

- **启动超时强杀**：`BOOT_TIMEOUT`（180 秒）内端口未监听，强制结束僵死的 java 进程并计一次失败
- **连续失败熔断**：连续 `MAX_FAIL`（3 次）启动失败后守护退出并写入放弃锁，`COOLDOWN_MIN`（10 分钟）内不再自动拉起，避免无限重启风暴；删除 jar 目录下 `fail-N.lock` 可立即重试
- **日志可写探测**：拉起 java 前先探测日志文件是否被占用，防止日志锁死导致反复启动失败
- **打包期间等待**：jar 文件缺失（正在重新打包）时轮询等待，不会误判失败
- **人工停止保护**：`stop` 写入 `service-stop.flag`，看门狗检测到标记后保持停止，不会反向拉起
- **状态可查**：计划任务 ACL 已放开，普通用户（非管理员）也能执行 `status` 查询 SYSTEM 计划任务

### 配置修改

服务列表在脚本头部配置区，每个服务对应一组 `JAR_N` / `PORT_N`，新增服务时追加配置并把 `SVC_COUNT` 改为服务总数（最多支持 9 个）：

```bat
set "JAR_1=F:\backstage\deviceJar\ruoyi-admin.jar"
set "PORT_1=8686"
set "JAR_2=F:\backstage\moldJar\ruoyi-admin.jar"
set "PORT_2=8585"
set "SVC_COUNT=2"
```

日志、停止标记、失败计数均存放在各 jar 所在目录的 `logs` 内。

> 编码说明：本脚本以 UTF-8 编码保存，入口处 `chcp 65001` 切换控制台代码页，双击 / 计划任务运行时中文均正常显示。

## 更新记录

| 日期 | 内容 |
|---|---|
| 2026-09-12 | 新增 Windows 服务看门狗脚本 `ruoyi-watchdog.bat`：双层守护（窗口守护 + 计划任务兜底巡查）、启动超时强杀、连续失败熔断、人工停止保护；脚本以 UTF-8 编码保存并设置 `chcp 65001`，修复中文乱码问题 |
