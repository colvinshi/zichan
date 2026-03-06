@echo off
chcp 65001 >nul

echo ====================================
echo === GitHub 上传脚本 ===
echo ====================================
echo.

cd /d "%~dp0"

echo [1/6] 初始化Git仓库...
git init

echo [2/6] 添加所有文件...
git add .

echo [3/6] 提交文件...
git commit -m "feat: 初始化资产管理系统

- 后端: Spring Boot 2.7 + Java 17
- 前端: Vue 3 + Element Plus + Vite  
- 移动端: uni-app
- Docker部署配置
- 技术文档"

echo [4/6] 设置远程仓库...
git remote add origin https://github.com/colvinshi/zichan.git

echo [5/6] 设置分支...
git branch -M main

echo [6/6] 推送到GitHub...
git push -u origin main

echo.
echo ====================================
echo === 上传完成 ===
echo ====================================
echo.
pause
