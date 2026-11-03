FROM maven:3.9.11-eclipse-temurin-21

WORKDIR /app

# Tách pom.xml để tận dụng cache dependency giữa các lần build.
COPY pom.xml ./
RUN mvn -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests clean package

RUN mkdir -p /app/storage

ENV APP_STORAGE_ROOT=/app/storage
ENV APP_SEED_DEMO_DATA=false
ENV TZ=Asia/Ho_Chi_Minh

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar /app/target/HeThongKiemDinh-1.0.0.jar"]
