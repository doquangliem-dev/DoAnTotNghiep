# Bổ sung Trung tâm Quản lý chất lượng

- VT006 đổi tên từ Bộ phận khảo thí thành Trung tâm Quản lý chất lượng.
- Tương thích tài khoản/role cũ: Auth vẫn nhận từ khóa Khảo thí và tự cập nhật tên role VT006 khi khởi động.
- Khu vực /khao-thi được mở rộng thành module TT QLCL.
- Màn hình: Tổng quan, Theo dõi CTĐT, Phê duyệt kế hoạch, Theo dõi tiến độ, Báo cáo tự đánh giá, Minh chứng, Theo dõi phân công, Theo dõi kết quả, Thống kê tổng hợp.
- Các màn hình CTĐT/minh chứng/phân công/tiến độ là chỉ xem; nghiệp vụ phê duyệt vẫn dùng endpoint riêng của TT QLCL.
- Trạng thái kế hoạch mới dùng “Chờ TT QLCL duyệt”; backend vẫn nhận “Chờ khảo thí duyệt” để tương thích dữ liệu cũ.
