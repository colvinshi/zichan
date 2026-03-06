# 构建阶段
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

# 设置工作目录
WORKDIR /build

# 复制pom文件
COPY pom.xml .
COPY common/pom.xml ./common/
COPY core/pom.xml ./core/
COPY modules/pom.xml ./modules/

# 下载依赖（利用Maven缓存）
RUN mvn dependency:go-offline -B

# 复制源码
COPY . .

# 构建项目
RUN mvn clean package -DskipTests -B

# 运行阶段
FROM eclipse-temurin:17-jre-alpine

# 设置工作目录
WORKDIR /app

# 从构建阶段复制jar包
COPY --from=builder /build/core/target/*.jar app.jar

# 创建日志目录
RUN mkdir -p /app/logs

# 设置时区
RUN echo "Asia/Shanghai" > /etc/timezone

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "-Xms512m", "-Xmx1024m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "/app/app.jar"]
