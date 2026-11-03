# Docker fix

Bản này chỉ dùng image `maven:3.9.11-eclipse-temurin-21` cho cả build và chạy ứng dụng.
Không còn phụ thuộc `eclipse-temurin:21-jre`.

Chạy thủ công:

```powershell
docker compose down
docker compose build --pull=false
docker compose up
```

Hoặc double-click `docker-start.cmd`.
