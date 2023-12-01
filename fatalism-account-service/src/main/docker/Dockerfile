FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/fatalism-account-service.jar /app/fatalism-account-service.jar

EXPOSE 8080

# 设置容器启动时运行的命令
ENTRYPOINT ["java", "-jar", "fatalism-account-service.jar"]