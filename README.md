# HeThongKiemDinh - bản hệ thống cũ đã khôi phục

Đây là **giao diện và cấu trúc hệ thống cũ** của đồ án, không phải một project viết lại từ đầu. Bản này giữ nguyên layout/menu/luồng nghiệp vụ cũ và chỉ sửa các lỗi làm hệ thống trắng trang hoặc không có dữ liệu.

## Công nghệ
- Java 17
- Spring Boot 3.5.5
- Spring MVC + Thymeleaf
- Spring Data MongoDB / MongoDB Atlas
- Maven
- BCrypt cho mật khẩu

## Các lỗi đã sửa
- Sửa lỗi cú pháp thật trong `static/js/common.js` làm JavaScript toàn hệ thống không chạy và nhiều trang chỉ hiện khung trắng.
- Bổ sung hiển thị lỗi toàn cục ở giao diện thay vì lỗi API nhưng màn hình im lặng.
- Trang Quản lý CTĐT có xử lý lỗi tải dữ liệu rõ ràng.
- Bổ sung bộ khởi tạo dữ liệu MongoDB bằng **đúng Java model** để tránh dữ liệu cũ sai kiểu gây lỗi mapping.
- Seeder chỉ khởi tạo bộ demo khi các collection nghiệp vụ đang trống. Nếu database đã có dữ liệu nhưng thiếu/sai cấu trúc, hệ thống bỏ qua và ghi cảnh báo; không tự xóa collection hoặc ghi đè hồ sơ hiện có.
- Giữ bảo vệ `/nhan-su-bao-cao` và `/api/admin/mongo-check`.
- Không dùng Spring Boot DevTools để tránh lỗi Windows khóa file trong `target`.

## Dữ liệu nghiệp vụ chuẩn
- Bộ tiêu chuẩn chính: `MOET`.
- 8 Tiêu chuẩn: `TC1` ... `TC8`.
- 52 Tiêu chí: `TC1.1` ... `TC8.5`.
- Mã `H...` chỉ dành cho minh chứng, ví dụ `H1.1.01`.
- 5 nhóm công tác.
- Mỗi nhóm có Trưởng nhóm, Thư ký nhóm và Thành viên.
- Thư ký nhóm vẫn mang vai trò hệ thống `VT004 - Thành viên nhóm`, chức vụ được lưu trong nhóm.
- Mỗi Thành viên/Thư ký nhóm tối đa 2 Tiêu chí trong cùng nhóm/kế hoạch.
- Phiên bản CTĐT chỉ dùng năm chẵn. Trong source hiện tại chỉ có tài liệu nguồn xác thực cho PB2022 và PB2024; PB2024 là phiên bản mới nhất đang áp dụng. Chỉ bổ sung PB2026 khi có tài liệu CTĐT 2026 thực tế.

## 7 vai trò hệ thống
- `VT001` Quản trị
- `VT002` Hội đồng tự đánh giá
- `VT003` Trưởng nhóm tiêu chí
- `VT004` Thành viên nhóm
- `VT005` Thư ký chương trình
- `VT006` Bộ phận khảo thí / TT Quản lý chất lượng
- `VT007` Nhân sự phụ trách báo cáo

## Báo cáo tự đánh giá toàn CTĐT
Báo cáo gồm 4 phần:
1. Phần 1 - Khái quát: Admin giao nhân sự `VT007` phụ trách.
2. Phần 2 - Tự đánh giá theo Tiêu chuẩn/Tiêu chí: lấy từ báo cáo tiêu chí và báo cáo nhóm.
3. Phần 3 - Kết luận: Admin giao nhân sự `VT007` phụ trách.
4. Phần 4 - Phụ lục: Admin giao nhân sự `VT007` phụ trách.

Workflow báo cáo tiêu chí:
`Thành viên 25% -> Trưởng nhóm 50% -> Hội đồng cấp Khoa 75% -> TT QLCL 100%`.

Workflow minh chứng:
`Thành viên 30% -> Trưởng nhóm 60% -> Thư ký chương trình 100%`.

## Tự tạo dữ liệu demo
Mặc định trong `application.properties`, Dockerfile và Render là `false`:
```properties
app.seed-demo-data=false
```
Chỉ bật seed demo có chủ đích trên database thử nghiệm.

Khi các collection nghiệp vụ đang trống, ứng dụng khởi tạo bộ dữ liệu demo chuẩn. Nếu phát hiện dữ liệu hiện hữu không đồng bộ (kể cả số lượng thừa/thiếu), ứng dụng không tự reset; seeder ghi cảnh báo và dừng để tránh mất dữ liệu người dùng. Việc sửa dữ liệu hiện hữu phải thực hiện bằng migration có phạm vi, sau khi sao lưu và đối chiếu.

Nếu database đã có dữ liệu, seeder không xóa collection để ép về bộ demo. Dữ liệu hiện hữu được giữ nguyên để rà soát/migration riêng.

Có thể tắt bằng:
```powershell
$env:APP_SEED_DEMO_DATA='false'
```

## Tài khoản Admin mặc định
```text
Email: admin@huit.edu.vn
Mật khẩu: 123456
```

## Chạy trên Windows PowerShell
```powershell
cd D:\HUIT\Nam4\KhoaLuan\Code\HeThongKiemDinh
$env:MONGODB_URI='mongodb+srv://USERNAME:PASSWORD@cluster01.pjmul.mongodb.net/HeThongKiemDinh'
mvn clean spring-boot:run
```

Hoặc:
```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\run.ps1
```

Mở:
```text
http://localhost:8080/login
```

Kiểm tra MongoDB sau khi đăng nhập Admin:
```text
http://localhost:8080/api/admin/mongo-check
```

Sau khi seed thành công, log có dòng dạng:
```text
[SEED OK] HeThongKiemDinh ready -> CTDT=1, MOET=1, TC=8, criteria=52, groups=5, assignments=60, users=61
```

## Đường dẫn theo vai trò
- Admin: `/admin`
- Hội đồng: `/hoi-dong`
- Trưởng nhóm: `/truong-nhom`
- Thành viên: `/thanh-vien`
- Thư ký chương trình: `/thu-ky`
- Bộ phận khảo thí / QLCL: `/khao-thi`
- Nhân sự phụ trách báo cáo: `/nhan-su-bao-cao`

## Cảnh báo script reset MongoDB

`mongo/RESET_MONGO_THEO_EXCEL.js` và `.txt` có lệnh `db.dropDatabase()` và sẽ xóa toàn bộ database đang chọn. Đây là script lưu trữ cho môi trường demo; tuyệt đối không chạy trên MongoDB thật/production. Ưu tiên seeder an toàn và migration có phạm vi.

## Sửa lỗi seed dữ liệu nhóm
Bản sửa này phát hiện cả dữ liệu demo bị **thừa**. Ví dụ MongoDB cũ còn 6 nhóm trong khi hệ thống yêu cầu đúng 5 nhóm, Seeder sẽ tự dọn các collection nghiệp vụ demo và tạo lại bộ dữ liệu chuẩn thay vì dừng ứng dụng ở bước `validateSeed()`.

## Cập nhật chống phân công trùng
- Admin: Tiêu chuẩn đã được phân công trong kế hoạch đang chọn sẽ bị vô hiệu hóa trong combobox và hiển thị nhóm đang phụ trách.
- Trưởng nhóm: Tiêu chí đã được phân công trong nhóm + kế hoạch đang chọn sẽ bị làm mờ, khóa checkbox và hiển thị người đang thực hiện.
- Backend vẫn kiểm tra trùng khi lưu để tránh trường hợp gọi API trực tiếp hoặc mở nhiều cửa sổ cùng lúc.

## Cập nhật chức năng vai trò (10/10/2026)

Bản HeThongKiemDinh này tiếp tục giữ nguyên tên project và bổ sung hoàn thiện luồng nghiệp vụ cho các vai trò còn lại:

- Thành viên nhóm: theo dõi công việc, nộp/thay minh chứng, xem feedback duyệt minh chứng, soạn/gửi lại báo cáo tiêu chí và xem feedback các cấp.
- Thư ký nhóm: chỉ thành viên được gán chức vụ `Thư ký nhóm` mới thấy chức năng tổng hợp; xem nguồn báo cáo tiêu chí theo đúng Kế hoạch + Nhóm + Tiêu chuẩn; chỉ gửi báo cáo nhóm khi tất cả báo cáo tiêu chí nguồn đã qua Trưởng nhóm tối thiểu 50%; xem phản hồi của Trưởng nhóm.
- Thư ký chương trình: lập kế hoạch, xem phản hồi từ Khảo thí, duyệt minh chứng cấp cuối, quản lý minh chứng cấp Trường/Khoa, theo dõi báo cáo tiêu chí/báo cáo nhóm/Phần 1-3-4 và tổng hợp kết quả TC1-TC8.
- Các luồng Hội đồng, Khảo thí/QLCL, Nhân sự phụ trách báo cáo tiếp tục dùng cơ chế duyệt nhiều cấp hiện có.

Lưu ý: `Thư ký nhóm` không phải vai trò hệ thống riêng; tài khoản vẫn là VT004 - Thành viên nhóm và được xác định qua chức vụ trong `nhomTieuChi`.

---

## Chạy bằng Docker (khuyên dùng cho demo)

Bản này đã có sẵn `Dockerfile` và `docker-compose.yml`. Docker chỉ đóng gói **Spring Boot backend**; MongoDB vẫn dùng **MongoDB Atlas** hiện tại để không phải chuyển hoặc mất dữ liệu.

### 1. Chuẩn bị lần đầu

Yêu cầu:
- Docker Desktop đang chạy.
- MongoDB Atlas cho phép IP mạng hiện tại truy cập.
- Có chuỗi `MONGODB_URI` hợp lệ.

Tại thư mục project, tạo file `.env` từ mẫu:

```powershell
Copy-Item .env.example .env
notepad .env
```

Sửa dòng:

```env
MONGODB_URI=mongodb+srv://USERNAME:PASSWORD@YOUR_CLUSTER.mongodb.net/HeThongKiemDinh?retryWrites=true&w=majority
APP_SEED_DEMO_DATA=true
```

Không commit/upload file `.env` vì file này chứa tài khoản MongoDB Atlas. `.gitignore` đã được cấu hình để bỏ qua `.env`.

### 2. Chạy hệ thống

Cách nhanh nhất trên Windows: double-click `docker-start.cmd`.

Hoặc PowerShell:

```powershell
docker compose up --build
```

Khi log xuất hiện `Started ...`, mở:

```text
http://localhost:8080/login
```

Nhấn `Ctrl + C` để dừng khi đang chạy foreground.

### 3. Chạy nền

```powershell
docker compose up -d --build
```

Xem log:

```powershell
docker compose logs -f backend
```

Hoặc double-click `docker-logs.cmd`.

### 4. Dừng hệ thống

```powershell
docker compose down
```

Hoặc double-click `docker-stop.cmd`.

### 5. File minh chứng có bị mất không?

Không. `docker-compose.yml` dùng Docker volume:

```text
hethongkiemdinh_storage
```

được gắn vào `/app/storage`. Khi container được xóa/tạo lại, file minh chứng vẫn còn. Chỉ khi chủ động xóa volume (`docker compose down -v`) thì dữ liệu file trong volume mới bị xóa.

### 6. Khi sửa source Java/HTML/CSS

Chạy lại:

```powershell
docker compose up --build
```

Docker sẽ build image mới; các dependency Maven đã được cache nên các lần sau thường nhanh hơn lần đầu.

### 7. Kiểm tra container

```powershell
docker compose ps
```

Nếu backend chạy đúng sẽ có service `backend` và ánh xạ cổng `8080:8080`.

### 8. Nếu MongoDB Atlas không kết nối

Kiểm tra:
- `.env` chứa đúng `MONGODB_URI`.
- Mật khẩu có ký tự đặc biệt đã được URL-encode.
- Atlas `Network Access` đã cho phép IP mạng hiện tại.
- Không đặt URI trong `Dockerfile` hay commit `.env` lên GitHub.

## DEPLOY WEB

Bản này hỗ trợ chạy song song Docker local và deploy Render bằng Docker.

- Docker local: `docker compose up -d --build`
- Deploy Render: xem `DEPLOY_WEB.md`
- Blueprint Render: `render.yaml`
- Mẫu biến production: `.env.production.example`

## Storage local và production

- Docker/local: `APP_STORAGE_MODE=local` và named volume `hethongkiemdinh_storage`.
- Web production: `APP_STORAGE_MODE=supabase`, bucket private `hethongkiemdinh`.
- Hướng dẫn chi tiết: `DEPLOY_WEB_FREE_STORAGE.md`.
