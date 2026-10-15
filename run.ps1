$ErrorActionPreference = 'Stop'

Write-Host "=== HeThongKiemDinh29 ==="
Write-Host "Kiem tra tien trinh dang dung cong 8080..."

try {
    $connections = Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue
    if ($connections) {
        $pids = $connections | Select-Object -ExpandProperty OwningProcess -Unique
        foreach ($pidValue in $pids) {
            try {
                $proc = Get-Process -Id $pidValue -ErrorAction Stop
                Write-Host "Dung tien trinh PID $pidValue ($($proc.ProcessName)) dang chiem cong 8080..."
                Stop-Process -Id $pidValue -Force -ErrorAction Stop
                Start-Sleep -Milliseconds 700
            }
            catch {
                Write-Warning "Khong the dung PID $pidValue: $($_.Exception.Message)"
            }
        }
    }
}
catch {
    Write-Warning "Khong kiem tra duoc cong 8080: $($_.Exception.Message)"
}

if (Test-Path ".\target") {
    Write-Host "Xoa thu muc target cu..."
    $done = $false
    for ($i = 1; $i -le 5 -and -not $done; $i++) {
        try {
            Remove-Item ".\target" -Recurse -Force -ErrorAction Stop
            $done = $true
        }
        catch {
            if ($i -eq 5) { throw }
            Write-Warning "target dang bi khoa. Thu lai lan $($i + 1)/5..."
            Start-Sleep -Seconds 1
        }
    }
}

if (-not $env:MONGODB_URI) {
    Write-Host ""
    Write-Host "Chua co bien MONGODB_URI." -ForegroundColor Yellow
    Write-Host "Hay dat truoc khi chay, vi du:"
    Write-Host '$env:MONGODB_URI="mongodb+srv://<user>:<password>@cluster01.pjmul.mongodb.net/HeThongKiemDinh"'
    exit 1
}

Write-Host "Khoi dong Spring Boot..."
& mvn clean spring-boot:run
exit $LASTEXITCODE
