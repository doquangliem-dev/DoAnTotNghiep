# Deploy miễn phí + giữ file lâu dài

Bản này hỗ trợ 2 chế độ lưu file mà không đổi nghiệp vụ hay đường dẫn `/uploads/...` hiện có.

## 1. Chạy local / Docker

Docker luôn dùng storage local trong named volume `hethongkiemdinh_storage`.

```powershell
docker compose down
docker compose up -d --build
```

Mở: `http://localhost:8080`

`docker-compose.yml` ép `APP_STORAGE_MODE=local`, vì vậy cấu hình Supabase dành cho production không ảnh hưởng Docker local.

Không chạy `docker compose down -v` nếu muốn giữ file local.

## 2. Storage production

Production dùng Supabase Storage với bucket private.

Tạo một project Supabase, sau đó vào Storage và tạo bucket:

- Bucket name: `hethongkiemdinh`
- Public bucket: OFF / private

Không cần tạo public URL cho file. Spring Boot đọc file bằng service-role key và trả file qua `/uploads/**`. Các đường dẫn file cũ trong MongoDB vẫn có dạng `/uploads/ctdt/...`, `/uploads/minhchung/...`, `/uploads/minh-chung-chung/...`, `/uploads/bao-cao/...`.

## 3. Biến môi trường trên Render

Đặt các biến sau:

```text
MONGODB_URI=...
APP_SEED_DEMO_DATA=true
APP_STORAGE_MODE=supabase
SUPABASE_URL=https://YOUR_PROJECT.supabase.co
SUPABASE_SERVICE_ROLE_KEY=...
SUPABASE_STORAGE_BUCKET=hethongkiemdinh
APP_DEFAULT_ADMIN_EMAIL=admin@huit.edu.vn
APP_DEFAULT_ADMIN_PASSWORD=...
TZ=Asia/Ho_Chi_Minh
```

`SUPABASE_SERVICE_ROLE_KEY` là secret phía server. Không đưa khóa này vào Flutter, JavaScript phía trình duyệt hoặc GitHub.

## 4. Deploy Render

Push project lên GitHub. File `.env` đã nằm trong `.gitignore`, không commit secret.

Trên Render có thể tạo Web Service từ repository hoặc dùng `render.yaml`. Runtime là Docker, health check `/login`.

Khi deploy, Render chạy Spring Boot; dữ liệu nghiệp vụ nằm ở MongoDB Atlas; file nằm ở Supabase Storage. Render restart/redeploy không làm mất file upload.

## 5. Test sau deploy

1. Đăng nhập.
2. Upload một file CTĐT.
3. Mở file từ giao diện.
4. Upload một minh chứng và mở lại file.
5. Redeploy service Render.
6. Đăng nhập lại và mở các file đã upload. File phải vẫn còn.
7. Thử xóa/thay file để xác nhận object cũ được xử lý đúng.

## 6. Chuyển dữ liệu file local cũ lên cloud

Bản này không tự động copy các file đang nằm trong Docker volume lên Supabase. Các bản ghi MongoDB vẫn giữ đường dẫn tương đối `/uploads/...`, nên nếu production dùng cùng database với local mà file cũ chưa được upload lên Supabase, các file đó sẽ báo không tìm thấy.

Khuyến nghị dùng database production riêng. Khi cần chuyển dữ liệu thật, copy file từ volume local lên đúng thư mục tương ứng trong bucket:

- `ctdt/`
- `minhchung/`
- `minh-chung-chung/`
- `bao-cao/`

## 7. Kiến trúc

```text
Docker local
Spring Boot -> Docker volume /app/storage

Production
Browser / Mobile
      |
      v
Render Free - Spring Boot
      |                 |
      v                 v
MongoDB Atlas      Supabase Storage private
(dữ liệu)          (file CTĐT/minh chứng/báo cáo)
```
