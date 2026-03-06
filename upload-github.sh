#!/bin/bash

# 资产管理系统 - GitHub 上传脚本

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0YELLOW='\;32m'
033[1;33m'
NC='\033[0m'

echo -e "${GREEN}=== 资产管理系统 GitHub 上传 ===${NC}"

# 检查Git
if ! command -v git &> /dev/null; then
    echo -e "${RED}错误: 请先安装 Git${NC}"
    exit 1
fi

# 检查GitHub CLI
if ! command -v gh &> /dev/null; then
    echo -e "${YELLOW}警告: 未安装 GitHub CLI${NC}"
    echo "安装方法: https://cli.github.com/"
    echo ""
    echo "或者手动执行以下命令:"
    echo "  git init"
    echo "  git add ."
    echo "  git commit -m 'Initial commit'"
    echo "  git remote add origin https://github.com/YOUR_USERNAME/REPO_NAME.git"
    echo "  git push -u origin main"
    exit 1
fi

# 检查登录状态
if ! gh auth status &> /dev/null; then
    echo -e "${YELLOW}请先登录 GitHub:${NC}"
    gh auth login
fi

# 项目根目录
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$PROJECT_DIR"

echo -e "${YELLOW}[1/5] 初始化Git仓库...${NC}"
git init

echo -e "${YELLOW}[2/5] 创建初始提交...${NC}"
git add .
git commit -m "feat: 初始化资产管理系统

- 后端: Spring Boot 2.7 + Java 17
- 前端: Vue 3 + Element Plus + Vite  
- 移动端: uni-app
- Docker部署配置
- 技术文档"

echo -e "${YELLOW}[3/5] 获取仓库名称...${NC}"
read -p "请输入GitHub仓库名称 (例如: asset-management-system): " REPO_NAME

if [ -z "$REPO_NAME" ]; then
    REPO_NAME="asset-management-system"
fi

echo -e "${YELLOW}[4/5] 创建GitHub仓库...${NC}"
# 检查仓库是否已存在
if gh repo view "$REPO_NAME" &> /dev/null; then
    echo "仓库已存在，使用现有仓库"
else
    gh repo create "$REPO_NAME" --private --source=. --push
fi

echo -e "${YELLOW}[5/5] 推送到GitHub...${NC}"
git branch -M main
git remote add origin https://github.com/$(gh api user --jq '.login')/$REPO_NAME.git
git push -u origin main

echo ""
echo -e "${GREEN}=== 上传完成 ===${NC}"
echo ""
echo "仓库地址: https://github.com/$(gh api user --jq '.login')/$REPO_NAME"
