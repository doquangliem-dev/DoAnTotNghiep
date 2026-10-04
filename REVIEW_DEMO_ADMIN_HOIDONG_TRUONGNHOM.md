# HeThongKiemDinh25 Demo - Admin / Hội đồng / Trưởng nhóm

Bản này ưu tiên hoàn thiện các luồng dùng để trình bày:

## Admin
- Quản lý CTĐT và phiên bản/file: giữ các sửa lỗi cập nhật tức thời từ bản fixed trước.
- Quản lý nhóm: mỗi nhóm bắt buộc đúng 1 Trưởng nhóm + 1 Thư ký nhóm; Thư ký nhóm phải là tài khoản có vai trò hệ thống Thành viên nhóm.
- Người đã thuộc nhóm hoạt động khác không được thêm vào nhóm mới.
- Nhóm đã phát sinh bất kỳ phân công nào không thể xóa.
- Khi Admin phân Tiêu chuẩn, nhóm được gắn `maTieuChuan`; không cho một nhóm đang gắn Hx bị phân sang Hy khác.
- Chỉ kế hoạch `Đã phê duyệt` mới được dùng để phân công Tiêu chuẩn.

## Trưởng nhóm
- Phân công Tiêu chí được giới hạn đúng nhóm + kế hoạch + Tiêu chuẩn Admin đã giao.
- Bổ sung chọn Kế hoạch khi giao nhiều Tiêu chí để tránh nhầm khi cùng một Tiêu chuẩn xuất hiện ở nhiều đợt/kế hoạch.
- Người thực hiện chính là Thư ký nhóm hoặc Thành viên; Trưởng nhóm không tự được chọn làm người thực hiện chính.
- Duyệt minh chứng theo đúng phạm vi nhóm.
- Bổ sung `baoCaoNhom`: Thư ký nhóm tổng hợp, gửi Trưởng nhóm; Trưởng nhóm Duyệt / Yêu cầu chỉnh sửa.
- Màn `/truong-nhom/bao-cao-nhom` để duyệt báo cáo nhóm.

## Hội đồng
- Chỉ nhìn thấy báo cáo Tiêu chí đã gửi lên quy trình, không hiển thị bản `Đang soạn` của Thành viên.
- Chỉ báo cáo thuộc kế hoạch đã phê duyệt được đưa vào phạm vi làm việc của Hội đồng.
- Chỉ được đánh giá Tiêu chí khi báo cáo Tiêu chí đã được Hội đồng duyệt.
- Chỉ được đánh giá trong kế hoạch `Đã phê duyệt`.
- Tổng hợp Hội đồng có thêm bảng báo cáo nhóm đã được Trưởng nhóm duyệt.

## Thư ký nhóm
- Không phải vai trò hệ thống mới.
- Tài khoản vẫn là `VT004 - Thành viên nhóm`.
- Chức vụ được lưu trong `nhomTieuChi.thanhVienChiTiet[].chucVu = "Thư ký nhóm"` và `maThuKyNhom`.
- API tổng hợp nhóm nằm dưới `/api/thanh-vien/thu-ky-nhom/...` nên quyền được kiểm tra theo cả vai trò hệ thống Thành viên và chức vụ trong nhóm.

## Kiểm tra kỹ thuật trong môi trường đóng gói
- Java source đã được `javac` kiểm tra cú pháp và liên kết nội bộ bằng bộ stub framework cục bộ: không lỗi.
- 31 block JavaScript inline đã qua `node --check`: không lỗi cú pháp.
- Không có merge-conflict marker trong `src`.

Lưu ý: kiểm tra Java trên không thay thế `mvn clean package` thật với Spring Boot/MongoDB. Khi chạy trên máy phát triển, dùng Maven thật để xác nhận framework/runtime.
