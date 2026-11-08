# v6 – Đồng bộ phân công theo Excel Khoa học dữ liệu

## Thay đổi
- Bổ sung `maNguoiPhoiHopList` trong model `PhanCong` để phân biệt người đồng phụ trách với nhân sự hỗ trợ.
- Bổ sung `nhiemVuBoSung` trong model `NhomTieuChi` để lưu nhiệm vụ ngoài tiêu chí, hiện gồm CSDL kiểm định của Nhóm 5.
- Mở rộng các truy vấn công việc/phạm vi nhóm trong `HeThongService` để nhận diện người đồng phụ trách; kiểm tra không cho xóa thành viên đang đồng phụ trách tiêu chí.
- Thêm script `mongo/UPDATE_PHAN_CONG_THEO_EXCEL_KHDL.js`, sinh từ hai sheet `phancong` và `Tomtat` của file Excel bàn giao.

## Nguồn phân công
- 52 tiêu chí lấy từ sheet `phancong`.
- Trưởng nhóm, thư ký, thành viên, nhân sự hỗ trợ và nhiệm vụ CSDL kiểm định lấy từ sheet `Tomtat`.
- Người đầu tiên không ghi chú hỗ trợ là người phụ trách chính; người tiếp theo không ghi chú hỗ trợ được lưu là đồng phụ trách; người có ghi chú “hỗ trợ” lưu riêng trong `maNhanSuHoTroList`.
- Các tài khoản ánh xạ theo mã ND đã được đối chiếu với danh sách tài khoản MongoSH người dùng cung cấp.

## Cách chạy migration
Trong Mongosh, chọn database phù hợp rồi chạy nội dung file:
`mongo/UPDATE_PHAN_CONG_THEO_EXCEL_KHDL.js`

Script kiểm tra đủ 52 tiêu chí, 5 nhóm và tài khoản trước khi cập nhật. Script không xóa collection, báo cáo, minh chứng hoặc lịch sử. Nó cập nhật người viết báo cáo theo người phụ trách chính; không thay đổi `maNguoiUpload` của minh chứng.

## Kiểm thử
- Đã kiểm tra cú pháp JavaScript migration bằng `node --check`.
- Chưa thể chạy Maven build vì môi trường đóng gói không có Maven và project không kèm Maven Wrapper.
- Chưa kết nối MongoDB thực tế để chạy migration hoặc kiểm thử giao diện/runtime.
