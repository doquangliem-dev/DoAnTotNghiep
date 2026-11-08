# v6.3 – Gộp thao tác đánh giá/feedback/duyệt

## Thay đổi giao diện
- TTQLCL: báo cáo tiêu chí và minh chứng chỉ còn một nút thao tác; popup có lựa chọn duyệt/yêu cầu chỉnh sửa/feedback không đổi trạng thái.
- Trưởng nhóm: báo cáo tiêu chí và minh chứng dùng một nút thao tác; popup minh chứng có lựa chọn duyệt chuyển Thư ký/yêu cầu chỉnh sửa/feedback.
- Hội đồng: báo cáo phần và báo cáo tiêu chí dùng một nút xử lý; minh chứng dùng một nút đánh giá/phản hồi.
- Thư ký chương trình: báo cáo và minh chứng gộp các nút feedback/đánh giá/duyệt thành một nút.
- Giữ riêng liên kết Xem file trong bảng; chỉ gộp các thao tác xử lý hồ sơ.

## Quy trình nghiệp vụ giữ nguyên
- Thành viên → Trưởng nhóm (25% → 50%).
- Trưởng nhóm → Hội đồng (50% → 75%).
- Hội đồng → TTQLCL (75%).
- TTQLCL duyệt cuối (100%).
- Feedback riêng không tự đổi trạng thái duyệt; yêu cầu chỉnh sửa phải quay lại người phụ trách để sửa và gửi lại.

## Kiểm thử
- Không chạy được Maven build trong môi trường đóng gói vì không có lệnh `mvn`.
- Chưa có kết nối MongoDB/Render để kiểm thử runtime. Cần build/deploy thử trên môi trường dự án trước khi dùng chính thức.
