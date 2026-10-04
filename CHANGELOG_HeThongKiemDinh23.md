# HeThongKiemDinh24 – thay đổi chính

Phiên bản này tập trung sửa luồng nghiệp vụ Thành viên – Trưởng nhóm – Thư ký – Hội đồng và siết quyền ghi dữ liệu ở backend.

## 1. Phân quyền và an toàn dữ liệu
- Các API ghi dữ liệu quản trị dùng chung chỉ cho vai trò Quản trị.
- Các vai trò nghiệp vụ dùng API riêng `/api/<vai-tro>/...`.
- Thành viên/Trưởng nhóm chỉ được sửa và tải file cho minh chứng do chính mình tạo, đồng thời phải còn phân công hợp lệ.
- Các trường workflow như người tạo, phân công, kế hoạch và trạng thái được backend quản lý, không tin trực tiếp giá trị client gửi lên.
- Thành viên chỉ sửa báo cáo do chính mình viết và không thể đổi tiêu chí của báo cáo bằng ID khác.

## 2. Luồng minh chứng
Luồng chuẩn:

`Thành viên -> Chờ Trưởng nhóm duyệt -> Chờ Thư ký duyệt -> Đã duyệt`

- Trưởng nhóm chỉ xử lý minh chứng đang chờ Trưởng nhóm.
- Thư ký chỉ xử lý minh chứng đã qua Trưởng nhóm.
- Hội đồng chỉ xem minh chứng đã được duyệt hoàn tất; không còn duyệt tắt qua các cấp.
- Minh chứng đã duyệt bị khóa file.

## 3. Luồng báo cáo tự đánh giá
- Thành viên: `Đang soạn -> Chờ duyệt`.
- Hội đồng chỉ xử lý báo cáo ở trạng thái `Chờ duyệt`.
- Kết quả Hội đồng: `Đã duyệt` hoặc `Yêu cầu chỉnh sửa`.
- Hội đồng không sửa trực tiếp nội dung/tác giả báo cáo.
- Báo cáo đã duyệt bị khóa; lịch sử duyệt giữ người duyệt, nhận xét, khuyến nghị và thời gian.

## 4. Đánh giá tiêu chí của Hội đồng
- Chỉ được đánh giá khi báo cáo của tiêu chí đã được Hội đồng duyệt.
- `lanDanhGia` do server tự tăng, không do client quyết định.
- Bổ sung `maNguoiDanhGia` và `ngayDanhGia` để truy vết.
- Có thể gắn đánh giá với `maKeHoach`.

## 5. Kế hoạch và tổng hợp
- Minh chứng và báo cáo bổ sung `maKeHoach`.
- Tổng hợp Hội đồng có thể lọc theo kế hoạch, tránh trộn nhiều đợt tự đánh giá.
- Tỷ lệ hoàn thành dựa trên báo cáo **Đã duyệt**, đồng thời vẫn hiển thị tỷ lệ đã có báo cáo.
- Dữ liệu cũ có `maPhanCong` được suy ra `maKeHoach` khi có thể.

## 6. Sửa lỗi migration phân công
- Không còn chuyển mọi phân công cấp Tiêu chí thành phân công cấp Tiêu chuẩn khi ứng dụng khởi động.
- Không còn coi nhiều tiêu chí của cùng một thành viên trong một tiêu chuẩn là bản ghi trùng.
- Chỉ gom trùng các phân công **cấp Tiêu chuẩn** do Admin giao cho Trưởng nhóm.

## 7. Giao diện
- Hội đồng: Minh chứng chuyển sang chế độ xem dữ liệu đã duyệt; Báo cáo chỉ duyệt/trả chỉnh sửa; Đánh giá hiển thị kế hoạch, lần đánh giá, người đánh giá; Tổng hợp có lọc kế hoạch.
- Thành viên: báo cáo đang chờ duyệt/đã duyệt được khóa sửa; minh chứng dùng API theo vai trò.
- Trưởng nhóm/Thư ký: sử dụng API minh chứng đúng phạm vi nghiệp vụ.

## Kiểm tra tĩnh đã thực hiện
- Kiểm tra cú pháp Java ở mức parser bằng `javac` (môi trường không có dependency Spring/Maven nên không thể build đầy đủ tại đây).
- Kiểm tra cú pháp toàn bộ JavaScript inline trong các template bằng `node --check`.
- Tìm API ghi dữ liệu dùng chung trong template nghiệp vụ để bảo đảm các vai trò chính dùng endpoint riêng.
