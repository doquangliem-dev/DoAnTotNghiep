# Hoàn thiện web – bản v6.1

## Phạm vi đã chỉnh
- Thành viên được phân công phối hợp (`maNguoiPhoiHopList`) nhìn thấy tiêu chí trong danh sách công việc/báo cáo/minh chứng.
- Trưởng nhóm xem được minh chứng của người phụ trách chính, người phối hợp và nhân sự hỗ trợ.
- Backend cho phép người phối hợp cùng cập nhật nội dung báo cáo và tải/thay/xóa file báo cáo khi hồ sơ còn ở trạng thái chỉnh sửa.
- Chủ thể gửi duyệt vẫn là người phụ trách chính; người phối hợp không thể tự ý thay chủ thể hoặc gửi duyệt thay người phụ trách chính.
- Danh sách báo cáo của thành viên bao gồm tiêu chí được giao phối hợp.
- Giữ nguyên lịch sử duyệt/feedback khi cập nhật nội dung báo cáo theo luồng hiện hữu.

## Kiểm tra đã thực hiện
- `node --check mongo/UPDATE_PHAN_CONG_THEO_EXCEL_KHDL.js`: đạt.
- Kiểm tra cú pháp JavaScript nhúng trong 3 trang thành viên/trưởng nhóm đã sửa: đạt.
- Đã rà soát các nhánh quyền sửa/gửi duyệt/upload báo cáo trong `HeThongService`.

## Chưa thể xác nhận
- Maven build chưa chạy do môi trường không có Maven và project không có Maven Wrapper.
- Chưa chạy với MongoDB production, chưa đăng nhập thử đủ tài khoản 8 vai trò và chưa xác nhận Render runtime.
- Quyền đánh giá/feedback nhiều cấp có sẵn trong source v5/v6; bản này không thay đổi quy trình duyệt nhiều cấp ngoài quyền cộng tác báo cáo nêu trên.

## Triển khai
- Thay source bằng bản này, giữ nguyên biến môi trường Render/MongoDB.
- Chạy migration `mongo/UPDATE_PHAN_CONG_THEO_EXCEL_KHDL.js` nếu chưa chạy.
- Không chạy lại script reset dữ liệu demo.
