# HeThongKiemDinh24 - Sửa CTĐT và Quản lý nhóm

## Quản lý chương trình đào tạo
- Đồng bộ dữ liệu giao diện ngay từ response của API sau khi tạo/cập nhật CTĐT, tạo phiên bản, upload/xóa file; không cần F5/reset trang.
- GET API dùng `cache: no-store` để tránh đọc lại dữ liệu cũ từ trình duyệt.
- Sau upload, giao diện kiểm tra response phải có `fileDinhKem`; nếu backend không lưu file sẽ báo lỗi thay vì hiển thị giả là thành công.
- Upload/thay file cập nhật đồng thời cửa sổ Phiên bản và bảng CTĐT chính.
- Sửa trường năm phiên bản thành text để hỗ trợ cả `2026` và `2025-2026` đúng với validation/backend.

## Quản lý nhóm
- Người đang thuộc một nhóm hoạt động khác bị làm mờ và khóa checkbox khi tạo/chỉnh nhóm, tránh chọn nhầm.
- Backend cũng kiểm tra, không chỉ khóa ở giao diện. Dữ liệu cũ chồng chéo được phép giữ nguyên khi chỉnh nhóm, nhưng không cho thêm mới chồng nhóm.
- Thêm nút `Xóa nhóm`.
- Chỉ xóa được nhóm chưa từng phát sinh phân công. Nếu đã phân Tiêu chuẩn/công việc, nút xóa bị khóa và backend từ chối xóa để bảo toàn lịch sử.

## Kiểm tra
- Toàn bộ Java source compile thành công với bộ stub Spring/Mongo dùng để kiểm tra tham chiếu nội bộ.
- Toàn bộ JavaScript inline trong templates được parse kiểm tra, không có lỗi cú pháp.
