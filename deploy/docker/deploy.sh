#!/bin/bash

# 资产管理系统 Docker 部署脚本

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${GREEN}=== 资产管理系统 Docker 部署 ===${NC}"

# 检查Docker
if ! command -v docker &> /dev/null; then
    echo -e "${RED}错误: 请先安装 Docker${NC}"
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}错误: 请先安装 Docker Compose${NC}"
    exit 1
fi

# 切换到部署目录
cd "$(dirname "$0")/docker"

# 创建网络（如果不存在）
echo -e "${YELLOW}[1/5] 创建Docker网络...${NC}"
docker network create asset-network 2>/dev/null || true

# 构建镜像
echo -e "${YELLOW}[2/5] 构建Docker镜像（首次构建较慢）...${NC}"
docker-compose build

# 启动服务
echo -e "${YELLOW}[3/5] 启动服务...${NC}"
docker-compose up -d

# 等待服务健康
echo -e "${YELLOW}[4/5] 等待服务启动...${NC}"
sleep 30

# 检查状态
echo -e "${YELLOW}[5/5] 检查服务状态...${NC}"
docker-compose ps

echo ""
echo -e "${GREEN}=== 部署完成 ===${NC}"
echo ""
echo "服务访问地址:"
echo "  - 前端页面: http://localhost:8888"
echo "  - 后端API:  http://localhost:8888/api"
echo "  - RabbitMQ: http://localhost:15672 (账号: asset/Asset@123)"
echo ""
echo "查看日志: docker-compose logs -f"
echo "停止服务: docker-compose down"
