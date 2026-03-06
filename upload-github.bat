@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

:: 资产管理系统 GitHub 上传脚本 (Windows)

echo ====================================
echo === 资产管理系统 GitHub 上传 ===
echo ====================================
echo.

:: 检查Git
where git >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 请先安装 Git
    echo 下载地址: https://git-scm.com/
    pause
    exit /b 1
)

:: 检查GitHub CLI
where gh >nul 2>&1
if %errorlevel% neq 0 (
    echo [警告] 未安装 GitHub CLI
    echo.
    echo 请手动执行以下命令:
    echo   git init
    echo   git add .
    echo   git commit -m "Initial commit"
    echo   git remote add origin https://github.com/colvinshi/zichan.git
    echo   git push -u origin main
    echo.
    pause
    exit /b 1
)

:: 检查登录状态
gh auth status >nul 2>&1
if %errorlevel% neq 0 (
    echo 请先登录 GitHub
    gh auth login
)

echo [1/5] 初始化Git仓库...
git init

echo [2/5] 添加文件并提交...
git add .
git commit -m "feat: 初始化资产管理系统

- 后端: Spring Boot 2.7 + Java 17
- 前端: Vue 3 + Element Plus + Vite  
- 移动端: uni-app
- Docker部署配置
- 技术文档"

echo [3/5] 设置分支...
git branch -M main

echo.
set /p REPO_NAME=请输入GitHub仓库名称 (直接回车使用默认: asset-management-system):
if "!REPO_NAME!"=="" set REPO_NAME=asset-management-system

echo [4/5] 创建GitHub仓库并推送...
gh repo create %REPO_NAME% --private --source=. --push

echo.
echo ====================================
echo === 上传完成 ===
echo ====================================
echo.
echo 仓库地址: https://github.com/%REPO_NAME%
echo.
pause