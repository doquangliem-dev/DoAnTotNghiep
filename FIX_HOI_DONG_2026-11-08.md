# Cập nhật khu vực Hội đồng – 08/11/2026

- Bổ sung một thao tác thống nhất “Đánh giá / duyệt” cho bảng báo cáo tiêu chí; thao tác mở hộp xử lý đã có, gồm kết quả, nhận xét và khuyến nghị.
- Bổ sung một thao tác “Đánh giá / phản hồi” cho từng minh chứng, dùng hộp thoại thay vì hai nút rời Feedback/Đánh giá.
- API danh sách minh chứng Hội đồng hiện trả toàn bộ minh chứng tiêu chí để Hội đồng có thể xem/đánh giá, không chỉ các minh chứng đã có trạng thái “Đã duyệt”.
- Tài khoản/nhãn Hội đồng đã xuất hiện trong dữ liệu người dùng MongoDB mà người dùng cung cấp dưới mã ND002; vai trò hệ thống VT002 là “Hội đồng tự đánh giá”. Không thay đổi mật khẩu hoặc tạo tài khoản mới trong bản sửa này.

Kiểm tra: cú pháp JavaScript inline của hai trang Hội đồng đã qua `node --check`. Chưa chạy Maven build hoặc kiểm thử đăng nhập/runtime với MongoDB/Render.
