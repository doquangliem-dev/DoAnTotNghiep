# HeThongKiemDinh24 - rà soát và sửa lỗi

Ngày rà soát: 01/10/2026

## Các lỗi đã sửa trong bản 24

1. Chuẩn hóa luồng báo cáo Thành viên:
   - Lưu nội dung luôn là bản nháp `Đang soạn`.
   - Chuyển sang `Chờ duyệt` chỉ qua API `/api/thanh-vien/bao-cao/{id}/gui-duyet`.
   - Không còn khả năng request lưu trực tiếp tự đổi trạng thái và bỏ qua workflow.
   - Thành viên được gửi báo cáo khi đã hoàn thành đủ 4 phần; Hội đồng vẫn bắt buộc phải có minh chứng đã duyệt trước khi phê duyệt báo cáo `Đã duyệt`.

2. Sửa phạm vi dữ liệu theo đợt/kế hoạch:
   - Minh chứng Thành viên được lọc theo `maKeHoach + maTieuChi`.
   - Minh chứng Trưởng nhóm được lọc theo `maKeHoach + maTieuChuan`.
   - Báo cáo Trưởng nhóm được lọc theo `maKeHoach + maTieuChuan`.
   - Tránh lẫn dữ liệu khi cùng một bộ tiêu chí được sử dụng ở nhiều đợt tự đánh giá.

3. Giữ các kiểm tra nghiệp vụ quan trọng của bản 23 final:
   - Admin chỉ phân công theo kế hoạch đã được Khảo thí phê duyệt.
   - Trưởng nhóm chỉ giao tiêu chí trong phạm vi Tiêu chuẩn được Admin giao.
   - Minh chứng đi theo luồng Thành viên -> Trưởng nhóm -> Thư ký.
   - Hội đồng không duyệt tắt minh chứng.
   - Báo cáo đã gửi/đã duyệt bị khóa sửa.
   - Hội đồng chỉ được phê duyệt báo cáo khi tiêu chí có minh chứng đã duyệt.
   - Đánh giá tiêu chí được gắn người đánh giá, thời gian và kế hoạch.

## Kiểm tra kỹ thuật đã thực hiện

- Kiểm tra cú pháp toàn bộ JavaScript inline trong các template bằng Node.js: đạt.
- Kiểm tra biên dịch toàn bộ mã Java với bộ stub tương thích chữ ký Spring/Mongo để phát hiện lỗi cú pháp và lỗi tham chiếu nội bộ: đạt, không còn lỗi compile nội bộ.
- Kiểm tra không có marker merge conflict trong source.

Lưu ý: môi trường kiểm tra không tải được Maven dependencies từ Internet nên chưa chạy được `mvn package` thật với Spring Boot/MongoDB. Khi chạy trên máy phát triển có Maven và MongoDB, nên chạy `mvn clean package` rồi test theo luồng vai trò.
