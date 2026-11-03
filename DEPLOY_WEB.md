# Deploy HeThongKiemDinh lên Render

## 1. Chạy local bằng Docker vẫn giữ nguyên

```powershell
docker compose down
docker compose up -d --build
docker compose logs -f backend
```

Mở: `http://localhost:8080`

## 2. Chuẩn bị GitHub

Không commit file `.env`.

Các file cần có trên GitHub:

- `Dockerfile`
- `render.yaml`
- `pom.xml`
- `src/`

## 3. Deploy bằng Render Blueprint

1. Push thư mục dự án lên GitHub.
2. Đăng nhập Render.
3. Chọn **New > Blueprint**.
4. Chọn repository chứa dự án.
5. Render đọc `render.yaml` và tạo Web Service Docker.
6. Khi Render hỏi biến bí mật, nhập:
   - `MONGODB_URI`: URI MongoDB Atlas.
   - `APP_DEFAULT_ADMIN_PASSWORD`: mật khẩu quản trị dùng trên web.
7. Deploy.

Sau khi hoàn tất, Render cấp URL dạng:

`https://he-thong-kiem-dinh.onrender.com`

## 4. MongoDB Atlas

Nếu Render không kết nối được MongoDB Atlas, vào Atlas > Network Access và cho phép IP phù hợp. Đối với môi trường demo có thể dùng `0.0.0.0/0`, nhưng tài khoản MongoDB phải có mật khẩu mạnh.

Nên dùng database riêng cho web production nếu không muốn dữ liệu Docker local và web dùng chung.

## 5. File upload trên Render Free

Render Free dùng filesystem tạm. File upload trong `/app/storage` có thể mất khi service restart, redeploy hoặc spin down.

MongoDB Atlas không bị mất vì nằm ngoài Render.

Nếu cần giữ file minh chứng/CTĐT lâu dài, dùng một trong hai cách:

- nâng Render lên gói hỗ trợ Persistent Disk và mount `/app/storage`;
- chuyển file upload sang object storage như S3/Cloudinary.

## 6. Port

Project đã hỗ trợ cả hai môi trường:

- Docker/local: `8080`
- Render: lấy tự động biến `PORT`

Cấu hình:

`server.port=${PORT:8080}`

## 7. Tài khoản Admin

Local vẫn có giá trị mặc định để không làm hỏng workflow cũ.

Khi deploy web, `APP_DEFAULT_ADMIN_PASSWORD` được nhập trong Render và không lưu vào GitHub.

## 8. Mobile Flutter

Sau khi có URL Render, đổi API base URL từ:

`http://10.0.2.2:8080/api`

thành:

`https://<ten-service>.onrender.com/api`

Không cần sửa backend Docker local.

---

## Cập nhật: lưu file lâu dài ở gói free

Bản hiện tại đã hỗ trợ `APP_STORAGE_MODE=supabase` để không phụ thuộc filesystem tạm thời của Render. Hãy làm theo `DEPLOY_WEB_FREE_STORAGE.md`. Docker local vẫn dùng volume local và không bị ảnh hưởng.
