#!/bin/bash

# 资产管理系统 Docker 部署脚本

set -e

echo "=== Asset Management System Docker Deploy ==="

# 检查Docker
if ! command -v docker &> /dev/null; then
    echo "Error: Docker not installed"
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "Error: Docker Compose not installed"
    exit 1
fi

# 切换到部署目录
cd "$(dirname "$0")/deploy/docker"

# 构建镜像
echo "Building images..."
docker-compose build

# 启动服务
echo "Starting services..."
docker-compose up -d

# 等待服务启动
echo "Waiting for services..."
sleep 30

# 检查状态
echo "Service status:"
docker-compose ps

echo ""
echo "=== Deploy Complete ==="
echo "Frontend: http://localhost:8888"
echo "Backend API: http://localhost:8888/api"
echo "RabbitMQ: http://localhost:15672 (asset/Asset@123)"
echo ""
echo "View logs: docker-compose logs -f"
echo "Stop: docker-compose down"
