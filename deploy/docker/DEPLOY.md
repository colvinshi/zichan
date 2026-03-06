# Docker 部署步骤

## 1. 在服务器上克隆代码

```bash
git clone https://github.com/colvinshi/zichan.git
cd zichan
```

## 2. 进入Docker目录

```bash
cd deploy/docker
```

## 3. 启动所有服务

```bash
# Linux/Mac
docker-compose up -d

# Windows
docker-compose up -d
```

## 4. 查看服务状态

```bash
docker-compose ps
```

## 5. 查看日志

```bash
docker-compose logs -f
```

---

## 服务访问地址

| 服务 | 地址 |
|------|------|
| 管理后台 | http://localhost:8888 |
| 后端API | http://localhost:8888/api |
| RabbitMQ | http://localhost:15672 |
| MySQL | localhost:3306 |
| Redis | localhost:6379 |

---

## 常用命令

```bash
# 启动所有服务
docker-compose up -d

# 停止所有服务
docker-compose down

# 重启某个服务
docker-compose restart backend

# 查看日志
docker-compose logs -f backend

# 重新构建镜像
docker-compose build --no-cache
```

---

## 首次部署注意

1. 首次构建较慢（需要下载依赖）
2. 确保服务器开放端口：80, 3306, 5672, 6379, 8888, 15672
3. 默认数据库密码可在 `.env` 文件中修改
