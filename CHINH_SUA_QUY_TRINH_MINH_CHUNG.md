# Chỉnh sửa quy trình minh chứng và quyền Hội đồng

## Đã sửa
1. Các endpoint của Thành viên (`/thanh-vien/minh-chung`, upload, xóa file, gửi duyệt) gọi service với `leaderRole=false`.
   - Khi thành viên gửi minh chứng, trạng thái chuyển thành `Chờ Trưởng nhóm duyệt`.
   - Phạm vi phân công được kiểm tra theo vai trò thành viên, không dùng nhầm bộ lọc phân công của Trưởng nhóm.
2. Các endpoint của Trưởng nhóm vẫn giữ `leaderRole=true`.
   - Trưởng nhóm tự gửi minh chứng: `Chờ Thư ký duyệt`.
3. Hội đồng không còn thao tác duyệt/góp ý minh chứng trên giao diện.
   - Trang Hội đồng chỉ tra cứu minh chứng.
   - Đã gỡ endpoint POST `/hoi-dong/minh-chung/{id}/feedback`.
4. Luồng Hội đồng duyệt báo cáo được giữ nguyên.

## Luồng minh chứng
- Thành viên -> Trưởng nhóm -> Thư ký chương trình -> TT QLCL.
- Trưởng nhóm tự gửi -> Thư ký chương trình -> TT QLCL.
- Hội đồng không duyệt minh chứng.

## Kiểm tra
- Đã kiểm tra ZIP nguồn đầu vào và các thay đổi bằng rà soát source.
- Chưa chạy được Maven build trong môi trường đóng gói vì không có lệnh `mvn`; cần chạy `mvn clean package -DskipTests` trên máy Windows trước khi push/deploy.
