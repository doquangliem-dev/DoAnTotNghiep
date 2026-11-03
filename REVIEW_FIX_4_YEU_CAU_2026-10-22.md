# Sửa 4 yêu cầu ngày 2026-10-22

1. Quản lý nhóm
- Nhân sự đã thuộc nhóm chính bị làm mờ và không thể chọn khi tạo/sửa nhóm hỗ trợ.
- Backend cũng chặn việc đưa nhân sự nhóm chính sang nhóm hỗ trợ hoặc ngược lại.
- Hai nhóm hỗ trợ vẫn có thể dùng chung nhân sự nếu người đó không thuộc nhóm chính.

2. Quản lý nhân sự
- Đổi nhãn giao diện `Quản trị hệ thống` thành `Quản lý nhân sự`.
- Giữ nguyên route `/admin/he-thong` để không làm hỏng liên kết cũ.

3. Minh chứng cấp Trường/Khoa
- Trưởng nhóm: có bảng tham khảo riêng, chỉ xem/tải file.
- Thành viên/Thư ký nhóm: có bảng tham khảo riêng, chỉ xem/tải file.
- Thư ký chương trình: vẫn xem được nhưng không còn nút thêm/sửa/xóa minh chứng dùng chung.
- Backend chỉ cho Admin thêm/sửa/xóa/upload minh chứng cấp Trường/Khoa.

4. Nhận diện Trưởng nhóm
- Sidebar và dashboard hiển thị rõ Trưởng nhóm số mấy và tên nhóm chính đang phụ trách.
