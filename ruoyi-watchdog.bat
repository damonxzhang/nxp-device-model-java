@echo off
rem ==================================================================
rem  RuoYi 服务看门狗脚本（秒级守护 + 计划任务兜底巡查）
rem
rem  用法：
rem    双击本文件                    - 启动全部服务，各开一个最小化守护窗口
rem    ruoyi-watchdog.bat stop       - 停止全部服务（看门狗也会自动退出）
rem    ruoyi-watchdog.bat status     - 查看全部服务/守护/计划任务状态
rem    ruoyi-watchdog.bat install    - 安装看门狗计划任务（开机自动巡查，防手动关闭）
rem    ruoyi-watchdog.bat uninstall  - 卸载计划任务
rem
rem  守护机制（两层）：
rem    内层：窗口守护 - java 退出后 RESTART_DELAY 秒内自动重启（每个服务一个守护窗口）
rem    外层：计划任务 - 每 SCAN_MINUTES 分钟运行一次 "task 服务号" 巡查，
rem          防止守护窗口被关、脚本被手动结束等异常情况
rem
rem  增加服务方法：
rem    在下面追加 set "JAR_3=..." 与 set "PORT_3=..."，并把 SVC_COUNT 改成 3
rem    最多支持 9 个服务；每个服务的日志、停止标记放在各自 jar 所在目录里
rem
rem  修复记录：
rem    - 修复"java 已在运行但端口未起"分支未解析 java 路径的问题，
rem      该分支一旦需要重启进程会用空路径拼出无效命令，导致连续失败后放弃
rem    - 每次拉起前重新校验 java 与 jar 是否存在（打包期间 jar 缺失改为等待）
rem    - 修复退出入口误入失败计数子程序、install 成败判断错位的问题
rem ==================================================================
setlocal enabledelayedexpansion

rem ========================= 配置区（按需修改） ===========================
rem ---- 服务列表：每个服务对应一个 JAR_N 和 PORT_N（N 从 1 开始连续编号） ----
set "JAR_1=F:\backstage\deviceJar\ruoyi-admin.jar"
set "PORT_1=8686"
set "JAR_2=F:\backstage\moldJar\ruoyi-admin.jar"
set "PORT_2=8585"
rem ---- 服务总数，必须与上面配置的最大 N 一致 ----
set "SVC_COUNT=2"
rem JVM 内存参数
set "JAVA_OPTS=-Xms256m -Xmx1024m"
rem 服务退出后自动重启的等待秒数
set "RESTART_DELAY=10"
rem 计划任务巡查间隔分钟（Windows 计划任务最小 1 分钟）
set "SCAN_MINUTES=1"
rem ---- fail-safe: give up after MAX_FAIL consecutive failed starts ----
set "MAX_FAIL=3"
rem ---- a start counts as failed when the port is not listening within BOOT_TIMEOUT seconds ----
set "BOOT_TIMEOUT=180"
rem ---- boot status poll interval in seconds ----
set "BOOT_POLL=5"
rem ---- after give-up, pause auto restart for COOLDOWN_MIN minutes ----
set "COOLDOWN_MIN=10"
rem 计划任务名前缀，实际注册为 前缀-1、前缀-2 ...
set "TASK_NAME=RuoyiDeviceWatchdog"
rem ==================================================================

rem 交互模式：双击/手动运行=1；计划任务巡查模式（task 服务号）=0，绝不暂停
set "INTERACTIVE=1"
if /i "%~1"=="task" set "INTERACTIVE=0"

rem ---------------- 分发命令 ----------------
if /i "%~1"=="stop" goto :DoStop
if /i "%~1"=="status" goto :DoStatus
if /i "%~1"=="install" goto :DoInstall
if /i "%~1"=="uninstall" goto :DoUninstall
if /i "%~1"=="task" goto :DoGuard
goto :StartAll

rem ==================== 启动全部服务 ====================
:StartAll
echo.
echo 正在启动全部 %SVC_COUNT% 个服务守护 ...
for /l %%n in (1,1,%SVC_COUNT%) do (
    call :ResolveSvc %%n
    if not errorlevel 1 (
        if exist "!STOP_FLAG!" del /f /q "!STOP_FLAG!"
        if exist "!FAIL_CNT_FILE!" del /f /q "!FAIL_CNT_FILE!"
        if exist "!FAIL_LOCK!" del /f /q "!FAIL_LOCK!"
        echo    [服务 %%n] 端口 !CUR_PORT!  -  !CUR_JAR!
        start "RuoYi-Guard-%%n" /min cmd /c call "%~f0" task %%n
    )
)
echo.
echo [成功] 全部守护进程已拉起（最小化窗口，请勿关闭）
echo    - 停止服务：%~nx0 stop
echo    - 查看状态：%~nx0 status
goto :ExitPause

rem ==================== 守护入口（task 服务号） ====================
:DoGuard
set "SVC_NO=%~2"
if "%SVC_NO%"=="" set "SVC_NO=1"
call :ResolveSvc %SVC_NO%
if errorlevel 1 exit /b 1
cd /d "%APP_DIR%" 2>nul
if not exist "%LOG_DIR%" mkdir "%LOG_DIR%"

rem ---------- 检查 1：人工停止标记存在 → 保持停止 ----------
if exist "%STOP_FLAG%" (
    echo [信息] 检测到停止标记，服务 %SVC_NO% 保持停止状态。
    goto :GuardExit
)

rem ---------- 检查 2：端口已监听 → 服务正在运行，无需重复拉起 ----------
netstat -ano | findstr /c:":%CUR_PORT% " | findstr "LISTENING" >nul 2>&1
if not errorlevel 1 (
    echo [信息] 服务 %SVC_NO% 运行中（端口 %CUR_PORT%），无需重复启动。
    goto :GuardExit
)

rem ---------- 预备：失败计数、java 路径 ----------
set /a WAIT_TICKS=RESTART_DELAY+1
set /a BOOT_POLL_P1=BOOT_POLL+1
set /a FAIL_COUNT=0
if "%INTERACTIVE%"=="0" call :LoadFailState
if "%SKIP_RUN%"=="1" goto :GuardExit
rem 注意：无论走哪个分支都必须先解析 java 路径。
rem 若"进程在跑但端口未起"分支直接跳到启动监视，一旦进程死掉需要重启，
rem 就会用空路径拼出无效命令，连续失败后彻底放弃（历史 bug，已修复）。
call :ResolveJava
call :IsSvcJavaRunning
if "%IS_RUNNING%"=="0" goto :DoGuardNoJava

rem ---- java 进程已存在但端口未监听（正在启动中）→ 只监视其启动 ----
call :GetDate
set "LOG_FILE=%LOG_DIR%\ruoyi-admin_%LOG_DATE%.log"
goto :BootPoll

:DoGuardNoJava
echo 服务 %SVC_NO% 守护运行中，日志目录：%LOG_DIR%
echo 守护期间请勿直接关闭本窗口；窗口被关后计划任务会兜底拉起。

:RunGuard
if exist "%STOP_FLAG%" goto :GuardStopped
netstat -ano | findstr /c:":%CUR_PORT% " | findstr "LISTENING" >nul 2>&1
if not errorlevel 1 goto :GuardRunning
call :GetDate
set "LOG_FILE=%LOG_DIR%\ruoyi-admin_%LOG_DATE%.log"
rem ---- 每次拉起前重新校验 java 与 jar（重启循环中 jar 可能正在重新打包） ----
if not defined JAVA_EXE call :ResolveJava
if not defined JAVA_EXE (
    echo [错误] 未找到可用的 java.exe，无法启动服务 %SVC_NO%。
    echo [%date% %time%] [看门狗] 服务 %SVC_NO% 未找到可用的 java.exe，无法启动 >> "%WATCHDOG_LOG%"
    goto :GuardExit
)
if not exist "%CUR_JAR%" (
    if not defined JAR_WARNED (
        set "JAR_WARNED=1"
        echo [提示] 未找到 jar 包，若正在重新打包会自动等待：%CUR_JAR%
        echo [%date% %time%] [看门狗] 服务 %SVC_NO% 未找到 jar 包，等待中：%CUR_JAR% >> "%WATCHDOG_LOG%"
    )
    ping -n %WAIT_TICKS% 127.0.0.1 >nul
    goto :RunGuard
)
rem ---- probe：先探测日志文件可写，否则 java 根本无法落地输出 ----
set "PROBE_MARK=GUARD_PROBE_%RANDOM%_%RANDOM%"
echo [%date% %time%] [guard] svc %SVC_NO% probe %PROBE_MARK% 2>nul >> "%LOG_FILE%"
findstr /c:"%PROBE_MARK%" "%LOG_FILE%" >nul 2>&1
if errorlevel 1 goto :LogLocked
echo [%date% %time%] [guard] svc %SVC_NO% launching service >> "%LOG_FILE%" 2>nul
start "" /b cmd /c ""%JAVA_EXE%" %JAVA_OPTS% -jar "%CUR_JAR%" >> "%LOG_FILE%" 2>&1"
set /a WAITED=0
:BootPoll
if exist "%STOP_FLAG%" goto :GuardStopped
ping -n %BOOT_POLL_P1% 127.0.0.1 >nul
set /a WAITED+=%BOOT_POLL%
netstat -ano | findstr /c:":%CUR_PORT% " | findstr "LISTENING" >nul 2>&1
if not errorlevel 1 goto :BootOk
call :IsSvcJavaRunning
if "%IS_RUNNING%"=="0" goto :BootDead
if %WAITED% LSS %BOOT_TIMEOUT% goto :BootPoll
call :KillSvcJava
set "FAIL_REASON=启动超时：端口 %BOOT_TIMEOUT% 秒内未监听，已强制结束僵死的 java 进程"
goto :BootFail
:BootDead
set "FAIL_REASON=java 在端口监听前就退出了"
echo [提示] java 启动后立即退出，请查看日志末尾定位原因：%LOG_FILE%
goto :BootFail
:LogLocked
call :FailOnce "日志文件被其他进程占用，可能有残留的 java 进程"
if defined GIVE_UP goto :GuardExit
echo [警告] 服务 %SVC_NO% 无法启动：日志文件被占用
echo [警告] %LOG_FILE%
echo [警告] %RESTART_DELAY% 秒后重试 - 第 %FAIL_COUNT%/%MAX_FAIL% 次
ping -n %WAIT_TICKS% 127.0.0.1 >nul
goto :RunGuard
:BootFail
netstat -ano | findstr /c:":%CUR_PORT% " | findstr "LISTENING" >nul 2>&1
if not errorlevel 1 goto :BootOk
call :FailOnce "%FAIL_REASON%"
if defined GIVE_UP goto :GuardExit
echo [警告] 服务 %SVC_NO% 启动失败：%FAIL_REASON%
echo [警告] %RESTART_DELAY% 秒后重试 - 第 %FAIL_COUNT%/%MAX_FAIL% 次
ping -n %WAIT_TICKS% 127.0.0.1 >nul
goto :RunGuard
:BootOk
set /a FAIL_COUNT=0
del /f /q "%FAIL_CNT_FILE%" >nul 2>&1
del /f /q "%FAIL_LOCK%" >nul 2>&1
echo [%date% %time%] [guard] svc %SVC_NO% service UP on port %CUR_PORT%, boot took about %WAITED%s >> "%LOG_FILE%" 2>nul
echo [信息] 服务 %SVC_NO% 已启动（端口 %CUR_PORT%），守护监视中 ...
:RunWait
if exist "%STOP_FLAG%" goto :GuardStopped
ping -n %WAIT_TICKS% 127.0.0.1 >nul
netstat -ano | findstr /c:":%CUR_PORT% " | findstr "LISTENING" >nul 2>&1
if not errorlevel 1 goto :RunWait
echo [%date% %time%] [guard] svc %SVC_NO% service process exited >> "%LOG_FILE%" 2>nul
call :KillSvcJava
echo [警告] 服务 %SVC_NO% 意外停止，%RESTART_DELAY% 秒后自动重启 ...
ping -n %WAIT_TICKS% 127.0.0.1 >nul
goto :RunGuard
:GuardStopped
echo [信息] 已检测到停止标记，服务 %SVC_NO% 守护退出。
goto :GuardExit
:GuardRunning
echo [信息] 服务 %SVC_NO% 已在运行（端口 %CUR_PORT%），本守护退出。
goto :GuardExit

:GuardExit
if "%INTERACTIVE%"=="1" pause
exit /b 0

rem ==================== stop：停止全部服务 ====================
:DoStop
echo.
echo 正在停止全部 %SVC_COUNT% 个服务 ...
for /l %%n in (1,1,%SVC_COUNT%) do call :StopOne %%n
echo.
echo 全部服务已停止（守护也已退出）。双击本脚本可重新启动。
goto :ExitPause

rem ---- 停止单个服务 ----
:StopOne
call :ResolveSvc %~1
if errorlevel 1 exit /b 1
echo.
echo [服务 %~1] 正在停止（端口 %CUR_PORT%）...
if not exist "%LOG_DIR%" mkdir "%LOG_DIR%"
rem 先写停止标记，防止看门狗把服务再拉起来
type nul > "%STOP_FLAG%"
del /f /q "%FAIL_CNT_FILE%" >nul 2>&1
del /f /q "%FAIL_LOCK%" >nul 2>&1

set "FOUND=0"
for /f "tokens=5" %%p in ('netstat -aon ^| findstr /c:":%CUR_PORT% " ^| findstr "LISTENING"') do (
    set "FOUND=1"
    echo    正在结束进程 PID=%%p ...
    taskkill /f /pid %%p >nul 2>&1
)

rem 再按 jar 完整路径兜底杀掉残留 java 进程（防止端口未监听但进程存在）
powershell -NoProfile -Command "Get-CimInstance Win32_Process | Where-Object {$_.Name -eq 'java.exe' -and $_.CommandLine -like '*%CUR_JAR%*'} | ForEach-Object { Stop-Process -Id $_.ProcessId -Force -ErrorAction SilentlyContinue }"

if "!FOUND!"=="0" (
    echo    未发现运行中的服务（端口 %CUR_PORT% 无监听进程）。
) else (
    ping -n 3 127.0.0.1 >nul
    echo [服务 %~1] 已停止，看门狗不会再自动拉起。
)
exit /b 0

rem ==================== status：查看全部服务状态 ====================
:DoStatus
echo.
echo ==================== RuoYi 服务状态（共 %SVC_COUNT% 个服务） ====================
for /l %%n in (1,1,%SVC_COUNT%) do call :StatusOne %%n
echo.
echo   说明：日志、停止标记存放在各自 jar 所在目录的 logs 内
echo ========================================================
goto :ExitPause

:StatusOne
call :ResolveSvc %~1
if errorlevel 1 exit /b 1
echo.
echo -------- [服务 %~1] %CUR_JAR% --------
set "LISTEN=0"
set "LISTEN_PID="
for /f "tokens=5" %%p in ('netstat -aon ^| findstr /c:":%CUR_PORT% " ^| findstr "LISTENING"') do (
    set "LISTEN=1"
    set "LISTEN_PID=%%p"
)
if "!LISTEN!"=="1" (
    echo   端口状态    运行中（端口 %CUR_PORT% 监听中，PID !LISTEN_PID!）
) else (
    echo   端口状态    未运行
)
set "JAVA_FLAG="
if not "!LISTEN_PID!"=="" (
    tasklist /fi "PID eq !LISTEN_PID!" 2>nul | findstr /i "java.exe" >nul 2>&1
    if not errorlevel 1 set "JAVA_FLAG=1"
)
powershell -NoProfile -Command "if (Get-CimInstance Win32_Process | Where-Object {$_.Name -eq 'java.exe' -and $_.CommandLine -like '*%CUR_JAR%*'}) { exit 1 }"
if not defined JAVA_FLAG if errorlevel 1 set "JAVA_FLAG=1"
if defined JAVA_FLAG (echo   java 进程    存在) else (echo   java 进程    不存在)
if exist "%STOP_FLAG%" (echo   停止标记    存在（人工停止，双击脚本可恢复启动）) else (echo   停止标记    无)
schtasks /query /tn "\%TASK_NAME%-%~1" >nul 2>&1
if not errorlevel 1 (
    echo   看门狗计划任务    已安装，每 %SCAN_MINUTES% 分钟巡查
) else (
    echo   看门狗计划任务    未安装，以管理员运行 install 可安装
)
if exist "%FAIL_LOCK%" (echo   放弃重试锁    存在（自动重启已暂停，删除该文件可立即重试）) else (echo   放弃重试锁    无)
exit /b 0

rem ==================== install：安装看门狗计划任务 ====================
:DoInstall
net session >nul 2>&1
if errorlevel 1 (
    echo [提示] 安装计划任务需要管理员权限，正在弹出授权窗口（请点"是"）...
    powershell -NoProfile -Command "try { Start-Process -FilePath '%~f0' -ArgumentList 'install' -Verb RunAs } catch { Write-Host ('授权失败：' + $_.Exception.Message) }"
    exit /b 0
)
for /l %%n in (1,1,%SVC_COUNT%) do call :InstallOne %%n
echo.
echo [完成] 全部看门狗计划任务已就绪。
goto :ExitPause

:InstallOne
call :ResolveSvc %~1
if errorlevel 1 exit /b 1
echo.
echo 正在创建计划任务 %TASK_NAME%-%~1（服务 %~1，端口 %CUR_PORT%，SYSTEM 账户后台运行）...
schtasks /delete /tn "\%TASK_NAME%-%~1" /f >nul 2>&1
powershell -NoProfile -ExecutionPolicy Bypass -Command "try { $action = New-ScheduledTaskAction -Execute '\"%~f0\"' -Argument 'task %~1'; $trigger = New-ScheduledTaskTrigger -Once -At (Get-Date).AddMinutes(1) -RepetitionInterval (New-TimeSpan -Minutes %SCAN_MINUTES%) -RepetitionDuration (New-TimeSpan -Days 3650); $principal = New-ScheduledTaskPrincipal -UserId 'SYSTEM' -LogonType ServiceAccount -RunLevel Highest; $settings = New-ScheduledTaskSettingsSet -ExecutionTimeLimit ([TimeSpan]::Zero) -MultipleInstances IgnoreNew -AllowStartIfOnBatteries -DontStopIfGoingOnBatteries -StartWhenAvailable; Register-ScheduledTask -TaskName '%TASK_NAME%-%~1' -Action $action -Trigger $trigger -Principal $principal -Settings $settings -ErrorAction Stop | Out-Null; exit 0 } catch { Write-Host $_.Exception.Message; exit 1 }"
rem 必须紧跟在注册命令之后判断成败，否则检查的是后一条命令的返回值（历史 bug，已修复）
if errorlevel 1 (
    echo [失败] 计划任务 %TASK_NAME%-%~1 创建失败，请看上方错误信息。
    exit /b 1
)
rem 放开 ACL：普通用户（非管理员）也能查询 SYSTEM 计划任务的状态
powershell -NoProfile -Command "try { $sd = New-Object -ComObject Schedule.Service; $sd.Connect(); $tk = $sd.GetFolder('\').GetTask('%TASK_NAME%-%~1'); $tk.SetSecurityDescriptor('D:P(A;;FA;;;BA)(A;;FA;;;SY)(A;;FRFX;;;AU)', 0) } catch {}"
echo [成功] 计划任务 %TASK_NAME%-%~1 已安装。
exit /b 0

rem ==================== uninstall：卸载计划任务 ====================
:DoUninstall
net session >nul 2>&1
if errorlevel 1 (
    echo [提示] 卸载计划任务需要管理员权限，正在弹出授权窗口...
    powershell -NoProfile -Command "try { Start-Process -FilePath '%~f0' -ArgumentList 'uninstall' -Verb RunAs } catch { Write-Host ('授权失败：' + $_.Exception.Message) }"
    exit /b 0
)
for /l %%n in (1,1,%SVC_COUNT%) do call :UninstallOne %%n
echo.
echo [完成] 计划任务卸载完毕，不影响运行中的服务。彻底停止请运行 stop。
goto :ExitPause

:UninstallOne
schtasks /query /tn "\%TASK_NAME%-%~1" >nul 2>&1
if errorlevel 1 (
    echo [信息] 计划任务 %TASK_NAME%-%~1 不存在，跳过
) else (
    schtasks /delete /tn "\%TASK_NAME%-%~1" /f
    echo [成功] 计划任务 %TASK_NAME%-%~1 已卸载。
)
exit /b 0

rem ==================== 子程序 ====================
:GetDate
for /f %%i in ('powershell -NoProfile -Command "Get-Date -Format yyyyMMdd"') do set "LOG_DATE=%%i"
goto :eof

rem ---- 按服务号解析 jar/端口/日志/停止标记路径 ----
:ResolveSvc
set "CUR_JAR="
set "CUR_PORT="
call set "CUR_JAR=%%JAR_%~1%%"
call set "CUR_PORT=%%PORT_%~1%%"
if not defined CUR_JAR (
    echo [错误] 服务 %~1 未配置，请检查 JAR_%~1
    exit /b 1
)
if not defined CUR_PORT (
    echo [错误] 服务 %~1 未配置端口，请检查 PORT_%~1
    exit /b 1
)
for %%i in ("%CUR_JAR%") do set "APP_DIR=%%~dpi"
set "LOG_DIR=%APP_DIR%logs"
set "STOP_FLAG=%APP_DIR%service-stop.flag"
set "WATCHDOG_LOG=%LOG_DIR%\watchdog.log"
set "FAIL_CNT_FILE=%APP_DIR%fail-%~1.count"
set "FAIL_LOCK=%APP_DIR%fail-%~1.lock"
exit /b 0

rem ---- 子程序：定位可用的 1.8 版 java.exe，结果存 JAVA_EXE ----
:ResolveJava
if defined JAVA_EXE goto :eof
set "JAVA_EXE="
if defined JAVA_HOME set "JAVA_HOME=%JAVA_HOME:"=%"
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" (
    "%JAVA_HOME%\bin\java.exe" -version 2>&1 | findstr /c:"1.8.0" >nul
    if not errorlevel 1 set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
)
if not defined JAVA_EXE for /d %%d in ("C:\Program Files\Java\jdk1.8*" "C:\Program Files\Java\jre1.8*" "C:\Program Files (x86)\Java\jdk1.8*" "C:\Program Files (x86)\Java\jre1.8*") do (
    if not defined JAVA_EXE if exist "%%d\bin\java.exe" set "JAVA_EXE=%%d\bin\java.exe"
)
if not defined JAVA_EXE for /f "delims=" %%i in ('where java 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%i"
if not defined JAVA_EXE if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
goto :eof

rem ---- 子程序：task 模式专用 - 读取失败计数、检查放弃锁是否过期 ----
:LoadFailState
set "SKIP_RUN=0"
if not exist "%FAIL_LOCK%" goto :LFailSkip
for /f %%i in ('powershell -NoProfile -Command "[int]((Get-Date) - [IO.File]::GetLastWriteTime($env:FAIL_LOCK).TotalMinutes)"') do set "LOCK_AGE=%%i"
if not defined LOCK_AGE goto :LFailSkip
if %LOCK_AGE% LSS %COOLDOWN_MIN% (
    set "SKIP_RUN=1"
    goto :eof
)
del /f /q "%FAIL_LOCK%" >nul 2>&1
:LFailSkip
if not exist "%FAIL_CNT_FILE%" goto :eof
set /p FAIL_COUNT=<"%FAIL_CNT_FILE%" 2>nul
goto :eof

:IsSvcJavaRunning
set "IS_RUNNING=0"
powershell -NoProfile -Command "if (Get-CimInstance Win32_Process | Where-Object {$_.Name -eq 'java.exe' -and $_.CommandLine -like '*%CUR_JAR%*'}) { exit 1 }"
if errorlevel 1 set "IS_RUNNING=1"
goto :eof

rem ---- 子程序：按 jar 完整路径杀掉本服务的 java 进程（尽力而为） ----
:KillSvcJava
powershell -NoProfile -Command "Get-CimInstance Win32_Process | Where-Object {$_.Name -eq 'java.exe' -and $_.CommandLine -like '*%CUR_JAR%*'} | ForEach-Object { Stop-Process -Id $_.ProcessId -Force -ErrorAction SilentlyContinue }"
goto :eof

:ExitPause
if "%INTERACTIVE%"=="1" pause
exit /b 0

rem ---- 子程序：记一次启动失败，连续 MAX_FAIL 次后放弃。%1 = 原因 ----
:FailOnce
set /a FAIL_COUNT+=1
set "GIVE_UP="
>> "%WATCHDOG_LOG%" echo [%date% %time%] [watchdog] 服务 %SVC_NO%（端口 %CUR_PORT%）启动失败：%~1 ，连续失败 %FAIL_COUNT%/%MAX_FAIL% 次
if "%INTERACTIVE%"=="0" >> "%FAIL_CNT_FILE%" echo %FAIL_COUNT%
if %FAIL_COUNT% GEQ %MAX_FAIL% (
    set "GIVE_UP=1"
    >> "%WATCHDOG_LOG%" echo [%date% %time%] [watchdog] 服务 %SVC_NO% 连续失败 %FAIL_COUNT% 次后放弃，守护退出。请解决问题后重新双击脚本启动。
    if "%INTERACTIVE%"=="0" type nul > "%FAIL_LOCK%"
)
goto :eof
