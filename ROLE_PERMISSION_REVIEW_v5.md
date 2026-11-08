# Rà soát và cập nhật phân quyền v5 – 2026-11-08

## Quyết định nghiệp vụ được giữ
- Trưởng nhóm, Hội đồng, Thư ký chương trình và TTQLCL đều có quyền **Đánh giá** và **Feedback/Góp ý** đối với báo cáo tiêu chí và minh chứng.
- Đánh giá/feedback độc lập chỉ ghi lịch sử nhận xét, không tự ý thay đổi trạng thái quy trình.
- Các thao tác Duyệt / Yêu cầu chỉnh sửa vẫn đi theo các bước duyệt hiện có.
- Trưởng nhóm chỉ được feedback đối với tiêu chí/minh chứng thuộc phạm vi tiêu chuẩn được giao.
- Thư ký nhóm vẫn là chức vụ nội bộ của thành viên VT004; trang tổng hợp nhóm chỉ mở cho người được chỉ định là Thư ký nhóm.

## Thay đổi source
1. Bổ sung API feedback/đánh giá báo cáo và minh chứng cho 4 vai trò:
   - `/api/truong-nhom/.../feedback`
   - `/api/hoi-dong/.../feedback`
   - `/api/thu-ky/.../feedback`
   - `/api/khao-thi/.../feedback`
2. Lịch sử báo cáo ghi vào `BaoCaoTieuChi.duyetBaoCao`; lịch sử minh chứng ghi vào `danhGiaMinhChung`.
3. Bổ sung nút Đánh giá và Feedback ở các màn hình rà soát tương ứng.
4. Siết API dùng chung: mặc định chỉ Admin; cho phép một số GET dữ liệu tham chiếu cần thiết. Bổ sung API danh sách người dùng dạng tối giản (id, họ tên, khoa) thay vì trả toàn bộ hồ sơ người dùng cho các màn hình nghiệp vụ.
5. Bảo vệ `/uploads/**`: Admin, Hội đồng, Thư ký chương trình và TTQLCL được xem hồ sơ phục vụ nhiệm vụ; Trưởng nhóm, Thành viên và Nhân sự báo cáo chỉ xem file trong phạm vi hồ sơ được giao/sở hữu.
6. Trang tổng hợp báo cáo nhóm chỉ mở cho người thực sự được phân công làm Thư ký nhóm.
7. TTQLCL lấy danh sách nhóm qua API namespace `/api/khao-thi/nhom`; Thư ký nhóm lấy nhóm qua thông tin phân công cá nhân.

## Kiểm tra đã thực hiện
- Kiểm tra cú pháp 42 khối JavaScript inline bằng `node --check`: 0 lỗi cú pháp.
- Đối chiếu route feedback/đánh giá của 4 vai trò và các hàm service lưu lịch sử.
- Kiểm tra các trang nghiệp vụ không còn gọi API `/api/nguoi-dung` đầy đủ.

## Chưa xác minh
- Chưa chạy Maven build/test vì môi trường hiện tại không có Maven và source không kèm Maven Wrapper.
- Chưa chạy ứng dụng kết nối MongoDB thật, chưa kiểm thử bằng tài khoản thật của từng vai trò.
- Chưa xác minh quyền file với từng cấu hình storage trên Render/Supabase.

## Lưu ý
Không chạy seeder hoặc migration phá hủy dữ liệu. Trước khi deploy cần backup MongoDB và kiểm thử đăng nhập 8 vai trò, đặc biệt 4 vai trò đánh giá/feedback.
