FROM gradle:8.5-jdk17 AS builder

WORKDIR /build

# копируем ВСЁ (иначе multi-module сломается)
COPY . .

# собираем только нужный модуль
RUN gradle :apps:analytics-service:build -x test --no-daemon


FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /build/apps/analytics-service/build/libs/*.jar app.jar

ENV HADOOP_USER_NAME=root

ENTRYPOINT ["java","--add-opens=java.base/sun.nio.ch=ALL-UNNAMED","--add-opens=java.base/java.nio=ALL-UNNAMED","--add-opens=java.base/sun.util.calendar=ALL-UNNAMED","-jar","/app/app.jar"]