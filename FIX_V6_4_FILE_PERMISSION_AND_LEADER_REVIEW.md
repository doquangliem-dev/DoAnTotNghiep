# v6.4 – Sửa duyệt minh chứng Trưởng nhóm và quyền xem minh chứng chung

## Đã sửa
- Sửa lỗi JavaScript tại nút `Đánh giá / duyệt` trong trang minh chứng Trưởng nhóm: biến `canReview` trước đây bị tham chiếu từ inline onclick ở ngoài scope callback, gây `ReferenceError` khi bấm nút. Giá trị thao tác nay được render thành chuỗi literal.
- Cho phép vai trò Trưởng nhóm và Thành viên xem/tải file minh chứng dùng chung cấp Trường/Cấp Khoa (`duongDanFile` và `duongDanTaiLieuThamChieu`).
- Không mở quyền ghi: API thêm/sửa/upload/xóa minh chứng chung vẫn chỉ cho Quản trị; controller hiện tại tiếp tục chặn POST/DELETE với vai trò khác.

## Kiểm tra đã thực hiện
- `node --check` cho JavaScript trang `truong-nhom/minh-chung.html`: đạt.
- Đã rà soát endpoint GET `/api/minh-chung-chung` được mở đọc chung; các endpoint ghi vẫn có kiểm tra Quản trị.
- Chưa chạy Maven/Docker build trong môi trường này (không có Maven/Docker CLI), chưa kết nối MongoDB/Supabase/Render để kiểm thử runtime.

## Lỗi Render 502
Ảnh 502 của Render chỉ chứng minh web service không phản hồi tại thời điểm chụp; không chứa log nguyên nhân. Cần mở Render Dashboard → service `he-thong-kiem-dinh` → Logs, chọn thời điểm deploy mới nhất và lấy đoạn log từ `Starting...` đến exception đầu tiên. Kiểm tra thêm Events để biết deploy có failed/health check failed hay instance spin down. Không nên đoán nguyên nhân hoặc sửa cấu hình biến môi trường khi chưa có log.
