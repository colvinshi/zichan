@echo off
chcp 65001 >nul

echo ====================================
echo GitHub Upload Script
echo ====================================
echo.

cd /d "%~dp0"

echo Step 1: Initialize Git...
git init

echo Step 2: Add files...
git add .

echo Step 3: Commit...
git commit -m "feat: init asset management system"

echo Step 4: Add remote...
git remote add origin https://github.com/colvinshi/zichan.git

echo Step 5: Rename branch...
git branch -M main

echo Step 6: Push to GitHub...
git push -u origin main

echo.
echo ====================================
echo Done!
echo ====================================
pause
