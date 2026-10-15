# HeThongKiemDinh29 - bản hệ thống cũ đã khôi phục

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
- Nếu dữ liệu nghiệp vụ đang thiếu/hỏng, hệ thống tự tạo lại bộ demo chuẩn nhưng **không xóa collection người dùng/vai trò**.
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
- Phiên bản CTĐT chỉ dùng năm chẵn: 2022, 2024, 2026...

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
Mặc định:
```properties
app.seed-demo-data=true
```

Khi MongoDB đang trống hoặc bộ dữ liệu nghiệp vụ không đủ 8 tiêu chuẩn / 52 tiêu chí / 5 nhóm / 60 phân công, ứng dụng sẽ tạo lại dữ liệu nghiệp vụ chuẩn.

Nếu database đã đầy đủ và đọc được bằng model hiện tại, dữ liệu nghiệp vụ **không bị reset mỗi lần restart**.

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
cd D:\HUIT\Nam4\KhoaLuan\Code\HeThongKiemDinh29
$env:MONGODB_URI='mongodb+srv://doliem667:roby2000@cluster01.pjmul.mongodb.net/HeThongKiemDinh'
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
[SEED OK] HeThongKiemDinh29 ready -> CTDT=1, MOET=1, TC=8, criteria=52, groups=5, assignments=60, users=61
```

## Đường dẫn theo vai trò
- Admin: `/admin`
- Hội đồng: `/hoi-dong`
- Trưởng nhóm: `/truong-nhom`
- Thành viên: `/thanh-vien`
- Thư ký chương trình: `/thu-ky`
- Bộ phận khảo thí / QLCL: `/khao-thi`
- Nhân sự phụ trách báo cáo: `/nhan-su-bao-cao`

## Sửa lỗi seed dữ liệu nhóm
Bản sửa này phát hiện cả dữ liệu demo bị **thừa**. Ví dụ MongoDB cũ còn 6 nhóm trong khi hệ thống yêu cầu đúng 5 nhóm, Seeder sẽ tự dọn các collection nghiệp vụ demo và tạo lại bộ dữ liệu chuẩn thay vì dừng ứng dụng ở bước `validateSeed()`.

## Cập nhật chống phân công trùng
- Admin: Tiêu chuẩn đã được phân công trong kế hoạch đang chọn sẽ bị vô hiệu hóa trong combobox và hiển thị nhóm đang phụ trách.
- Trưởng nhóm: Tiêu chí đã được phân công trong nhóm + kế hoạch đang chọn sẽ bị làm mờ, khóa checkbox và hiển thị người đang thực hiện.
- Backend vẫn kiểm tra trùng khi lưu để tránh trường hợp gọi API trực tiếp hoặc mở nhiều cửa sổ cùng lúc.

## Cập nhật chức năng vai trò (10/10/2026)

Bản HeThongKiemDinh29 này tiếp tục giữ nguyên tên project và bổ sung hoàn thiện luồng nghiệp vụ cho các vai trò còn lại:

- Thành viên nhóm: theo dõi công việc, nộp/thay minh chứng, xem feedback duyệt minh chứng, soạn/gửi lại báo cáo tiêu chí và xem feedback các cấp.
- Thư ký nhóm: chỉ thành viên được gán chức vụ `Thư ký nhóm` mới thấy chức năng tổng hợp; xem nguồn báo cáo tiêu chí theo đúng Kế hoạch + Nhóm + Tiêu chuẩn; chỉ gửi báo cáo nhóm khi tất cả báo cáo tiêu chí nguồn đã qua Trưởng nhóm tối thiểu 50%; xem phản hồi của Trưởng nhóm.
- Thư ký chương trình: lập kế hoạch, xem phản hồi từ Khảo thí, duyệt minh chứng cấp cuối, quản lý minh chứng cấp Trường/Khoa, theo dõi báo cáo tiêu chí/báo cáo nhóm/Phần 1-3-4 và tổng hợp kết quả TC1-TC8.
- Các luồng Hội đồng, Khảo thí/QLCL, Nhân sự phụ trách báo cáo tiếp tục dùng cơ chế duyệt nhiều cấp hiện có.

Lưu ý: `Thư ký nhóm` không phải vai trò hệ thống riêng; tài khoản vẫn là VT004 - Thành viên nhóm và được xác định qua chức vụ trong `nhomTieuChi`.
