@echo off
setlocal
cd /d "%~dp0"

echo Dang dung HeThongKiemDinh...
docker compose down

endlocal
pause
