# 资产管理系统

Enterprise Asset Management System

## 项目简介

一套完整的企业级资产管理系统，支持资产全生命周期管理、盘点任务执行、移动端扫码盘点等功能。

## 技术架构

### 后端
- Java 17
- Spring Boot 2.7.x
- Spring Security 5.7.x
- Spring Data JPA
- MySQL 8.0+
- Redis
- RabbitMQ

### 前端
- Vue 3.x
- Element Plus 2.x
- Vite 3.x
- TypeScript
- Pinia

### 移动端
- uni-app 3.x
- Vue 3.x
- uView 2.x

## 项目结构

```
├── asset-parent/          # 后端项目
│   ├── common/            # 通用模块
│   ├── core/              # 核心模块
│   └── modules/           # 业务模块
├── asset-web/             # 管理后台
├── asset-mobile/          # 移动端
└── docs/                  # 文档
```

## 快速开始

### 环境要求

- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Redis 6.2+
- Maven 3.8+

### 后端启动

```bash
cd asset-parent

# 创建数据库
mysql -u root -p < 创建 asset_db 数据库

# 修改配置文件
vim core/src/main/resources/application.yml

# 启动
mvn spring-boot:run
```

### 前端启动

```bash
# 管理后台
cd asset-web
npm install
npm run dev

# 移动端
cd asset-mobile
npm install
npm run dev:h5
```

## 默认账号

| 账号 | 密码 | 角色 |
|------|------|------|
| admin | Admin@123 | 管理员 |

## 功能模块

- **用户管理**: 用户CRUD、角色管理
- **资产管理**: 资产增删改查、分类管理、转移
- **盘点管理**: 任务创建、执行、结果审核
- **移动端**: 扫码盘点、资产查询

## API文档

详见 [技术方案文档](./docs/技术方案文档.md)

## 使用手册

详见 [使用手册](./docs/使用手册.md)

## License

MIT License


---

## 上传到 GitHub

### 方式一: 使用脚本 (推荐)

**Windows:**
```bat
双击运行 upload-github.bat
```

**Linux/Mac:**
```bash
chmod +x upload-github.sh
./upload-github.sh
```

### 方式二: 手动上传

```bash
# 1. 初始化
git init

# 2. 添加文件
git add .

# 3. 提交
git commit -m "feat: 初始化资产管理系统"

# 4. 创建远程仓库并推送
gh repo create asset-management-system --private --source=. --push
# 或
git remote add origin https://github.com/你的用户名/asset-management-system.git
git push -u origin main
```

### 前提条件

- 安装 [Git](https://git-scm.com/)
- 安装 [GitHub CLI](https://cli.github.com/) (可选)
- 已登录 GitHub 账号"# zichan" 
"# zichan" 
"# zichan" 
