@echo off
setlocal
cd /d "%~dp0"

echo ==========================================
echo   HeThongKiemDinh - Docker Start
echo ==========================================

where docker >nul 2>nul
if errorlevel 1 (
    echo [LOI] Khong tim thay Docker CLI. Hay mo Docker Desktop truoc.
    pause
    exit /b 1
)

if not exist ".env" (
    echo [LOI] Chua co file .env.
    echo Hay copy .env.example thanh .env va dien MONGODB_URI MongoDB Atlas.
    echo.
    echo Lenh PowerShell:
    echo   Copy-Item .env.example .env
    pause
    exit /b 1
)

echo Dang build/khoi dong backend...
docker compose build --pull=false
if errorlevel 1 (
    echo [LOI] Build Docker that bai.
    pause
    exit /b 1
)

echo Khoi dong backend...
docker compose up

endlocal
