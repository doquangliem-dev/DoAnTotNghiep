# Hệ thống quản lý hoạt động tự đánh giá và kiểm định chất lượng CTĐT – Web

## Công nghệ
- Java 21, Spring Boot, Spring MVC, Thymeleaf.
- Spring Data MongoDB và MongoDB Atlas.
- Maven.

## Cấu trúc
- `src/`: mã nguồn ứng dụng.
- `storage-seed/`: tài liệu PDF minh chứng và báo cáo được ứng dụng sử dụng.
- `pom.xml`: cấu hình Maven.
- `render.yaml`, `Dockerfile`: cấu hình triển khai.
- `.vscode/`, `.editorconfig`: cấu hình định dạng mã nguồn.

## Chạy trên Windows
Mở PowerShell tại thư mục `HeThongQuanLyTuDanhGiaKiemDinhCTDT_Web`, cấu hình URI MongoDB bằng biến môi trường rồi chạy:

```powershell
$env:MONGODB_URI='YOUR_MONGODB_CONNECTION_STRING'
mvn spring-boot:run
```

Mở `http://localhost:8080/login`.

Không đưa URI MongoDB, mật khẩu hoặc tệp `.env` thật vào gói nộp. Các tệp `.env.example` chỉ là mẫu cấu hình.

## Dữ liệu và an toàn
- Giữ nguyên bộ tiêu chuẩn MOET gồm 8 tiêu chuẩn và 52 tiêu chí.
- Không tự động xóa hoặc ghi đè dữ liệu MongoDB hiện có.
- Không chạy script reset database trên môi trường thật.
- Các PDF trong `storage-seed/` là dữ liệu tài liệu của hệ thống, không phải file build tạm.
