# Rà soát và tiếp tục dự án - 08/11/2026

## 1. Source đầu vào
- Tiếp tục từ ZIP `HeThongKiemDinh_FixCNTT_FileChucNang_Format_v2.zip`.
- Giữ cấu trúc thư mục gốc `HeThongKiemDinh/`.
- Không kết nối MongoDB thật, không deploy, không thực hiện thao tác xóa dữ liệu.

## 2. Các thay đổi đã thực hiện trong source

### 2.1. Bảo vệ dữ liệu MongoDB
- `DemoDataSeeder` kiểm tra trạng thái dữ liệu trước khi seed.
- Nếu collection nghiệp vụ đã có dữ liệu nhưng không khớp bộ demo, seeder bỏ qua việc seed/repair và ghi cảnh báo; không gọi thao tác xóa dữ liệu.
- Chỉ khởi tạo bộ demo khi các collection nghiệp vụ đang trống.
- Không thay đổi database đang chạy và không thực thi script Mongo.

### 2.2. Sửa ánh xạ phân công 5 nhóm
- NT001: TC1, TC2 (bổ sung ND047 để đủ tải 13 tiêu chí, giới hạn tối đa 2 tiêu chí/người)
- NT002: TC3, TC4
- NT003: TC5
- NT004: TC6, TC7 (bổ sung ND048 để đủ tải 14 tiêu chí, giới hạn tối đa 2 tiêu chí/người)
- NT005: TC8
- `needsRepair()` được bổ sung kiểm tra ánh xạ tiêu chuẩn của 5 nhóm. Nếu dữ liệu Mongo hiện tại khác, hệ thống chỉ cảnh báo/bỏ qua, không tự xóa để tạo lại.

### 2.3. Loại bỏ phiên bản CTĐT 2026 không có căn cứ
- Đối chiếu SHA-256 cho thấy `CTDT001_PB2024.pdf` và `CTDT001_PB2026.pdf` cũ giống hệt nhau. Cả hai PDF có metadata tạo ngày 18/10/2024.
- Không có tài liệu CTĐT 2026 riêng để xác nhận.
- Đã loại file PB2026 trùng khỏi `storage-seed`, cập nhật manifest và sửa seeder chỉ sử dụng PB2022/PB2024; PB2024 là phiên bản mới nhất đang áp dụng.
- Kế hoạch tự đánh giá năm 2026 vẫn là kế hoạch năm 2026, nhưng tham chiếu phiên bản CTĐT PB2024 đang có tài liệu nguồn.
- Không sửa database thật. Database đã có PB2026 cần được đối chiếu và migration riêng, không tự động xóa/cập nhật.

- ND047 và ND048 là tài khoản dự phòng VT004 đã có trong danh sách seed; được đưa vào thành viên bổ sung của NT001 và NT004 để bảo đảm không vượt giới hạn 2 tiêu chí/người.

## 3. Kiểm tra file PDF
Đếm trực tiếp sau thay đổi: 127 PDF, 423 trang.

| Nhóm file | Số PDF | Số trang |
|---|---:|---:|
| CTĐT | 2 | 69 |
| Minh chứng tiêu chí | 52 | 156 |
| Minh chứng dùng chung | 10 | 20 |
| Báo cáo | 63 | 178 |
| **Tổng** | **127** | **423** |

- `pdfinfo`: 0 lỗi.
- `pdftotext`: 0 lỗi; 0 file không trích xuất được văn bản.
- Nội dung các PDF là hồ sơ mẫu theo tiêu chí CNTT; chưa được xác minh là hồ sơ/minh chứng thực tế do đơn vị ban hành.

## 4. Kiểm tra mã nguồn có thể thực hiện trong môi trường hiện tại
- 42 khối JavaScript inline trong HTML: `node --check` không phát hiện lỗi cú pháp.
- 1 file JavaScript độc lập: `node --check` không phát hiện lỗi cú pháp.
- `DemoDataSeeder.java`: kiểm tra cấu trúc sơ bộ, ngoặc cân bằng; chưa biên dịch Java.
- Không có Maven hoặc Maven Wrapper trong môi trường rà soát, vì vậy chưa chạy được `mvn test` / `mvn package`.
- Chưa chạy runtime, chưa đăng nhập thử từng vai trò, chưa kết nối MongoDB/Supabase, chưa deploy Render.

## 5. Các điểm còn cần xử lý
1. Rà soát/migration ánh xạ nhóm trong MongoDB hiện có theo đúng mapping 5 nhóm; phải backup và xem dữ liệu trước.
2. Đối chiếu PB2026 đang có trong MongoDB (nếu có) với tài liệu CTĐT gốc; không tự nhận PB2024 là PB2026.
3. Kiểm thử runtime theo từng vai trò: Admin, Hội đồng, Trưởng nhóm, Thư ký nhóm (chức vụ trong nhóm, không phải role riêng), Thành viên, Thư ký chương trình, TTQLCL/Khảo thí, Nhân sự báo cáo.
4. Kiểm tra luồng duyệt và quyền backend/API bằng tài khoản kiểm thử.
5. Rà soát storage thực tế Supabase/Render và xác minh việc upload, thay file, xóa file, đọc file qua restart/deploy.
6. Cài/khởi chạy Maven để biên dịch và chạy test trước khi deploy.

## 6. Cấu hình kỹ thuật cần ghi nhớ
- `pom.xml` đặt `java.version=17`; Docker base image dùng Eclipse Temurin 21. Cần thống nhất mục tiêu Java sau khi kiểm tra yêu cầu triển khai, hiện chưa thay đổi cấu hình này.
- `APP_SEED_DEMO_DATA` mặc định false trong `application.properties`, Dockerfile và Render config.
- Không chạy `mongo/RESET_MONGO_THEO_EXCEL.js` hoặc `.txt` trên database thật: script có `dropDatabase()`.
