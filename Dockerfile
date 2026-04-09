FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# копируем уже собранный fatJar
COPY apps/analytics-service/build/libs/app.jar app.jar

ENV HADOOP_USER_NAME=root

ENTRYPOINT ["java","--add-opens=java.base/sun.nio.ch=ALL-UNNAMED","--add-opens=java.base/java.nio=ALL-UNNAMED","--add-opens=java.base/sun.util.calendar=ALL-UNNAMED","--add-opens=java.base/java.lang=ALL-UNNAMED","--add-opens=java.base/java.io=ALL-UNNAMED","--add-opens=java.base/sun.security.action=ALL-UNNAMED","-jar","/app/app.jar"]