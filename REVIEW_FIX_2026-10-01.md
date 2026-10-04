# Rà soát toàn bộ logic HeThongKiemDinh24

Ngày rà soát: 01/10/2026

Phạm vi: Admin, Hội đồng tự đánh giá, Trưởng nhóm tiêu chí, Thành viên nhóm, Thư ký chương trình, Bộ phận khảo thí.

## Các lỗi/logic đã chỉnh

1. Siết API dữ liệu toàn hệ thống: /api/phan-cong, /api/minh-chung, /api/bao-cao chỉ Admin được dùng; các vai trò nghiệp vụ chuyển sang API theo phạm vi riêng.
2. Thêm /api/truong-nhom/phan-cong chỉ trả phân công trong phạm vi Trưởng nhóm phụ trách.
3. Admin phân công Tiêu chuẩn bắt buộc chọn kế hoạch đã được Bộ phận khảo thí phê duyệt, không nhập mã kế hoạch tự do.
4. Kiểm tra Tiêu chuẩn phải thuộc đúng bộ tiêu chuẩn của kế hoạch; chặn phân công trùng cùng Tiêu chuẩn trong cùng kế hoạch.
5. Hủy phân công Tiêu chuẩn sẽ hủy các phân công Tiêu chí con cùng kế hoạch/nhóm/tiêu chuẩn.
6. Phân công Tiêu chí được tách theo kế hoạch, cho phép cùng một Tiêu chí xuất hiện ở chu kỳ tự đánh giá khác mà không bị coi là trùng.
7. Trạng thái phân công được backend quản lý; chặn request tự gán trạng thái tùy ý.
8. Tự cập nhật tiến độ Đã phân công -> Đang thực hiện -> Hoàn thành dựa trên minh chứng/báo cáo; Tiêu chuẩn chỉ Hoàn thành khi tất cả Tiêu chí của Tiêu chuẩn đã được giao và hoàn thành.
9. Chặn chỉnh sửa nhóm làm mồ côi phân công: không xóa/demote Trưởng nhóm, Thành viên, Nhân sự hỗ trợ khi còn công việc hoạt động; không ngừng nhóm khi còn phân công.
10. Kiểm tra vai trò hệ thống khi sửa người dùng để không làm hỏng nhóm/phân công đang tồn tại.
11. Minh chứng Thành viên/Trưởng nhóm được kiểm tra ownership, phạm vi phân công và trạng thái; minh chứng bị từ chối có thể thay file và nộp lại đúng cấp duyệt.
12. Báo cáo Thành viên lưu theo đúng phân công + kế hoạch; chặn trùng theo cùng người + tiêu chí + kế hoạch, không chặn chu kỳ mới.
13. Báo cáo phải đủ 4 phần và có ít nhất 1 minh chứng đã được Thư ký duyệt trước khi gửi Hội đồng.
14. Hội đồng chỉ duyệt báo cáo Chờ duyệt; không thể duyệt báo cáo chưa có minh chứng đã duyệt; khi yêu cầu chỉnh sửa công việc quay về Đang thực hiện.
15. Đánh giá Tiêu chí bắt buộc chọn kế hoạch, chỉ thực hiện sau khi báo cáo của đúng kế hoạch đã được duyệt; lần đánh giá/người/thời gian do backend ghi.
16. Kế hoạch tự đánh giá có state machine: Đang lập/Yêu cầu chỉnh sửa -> Chờ khảo thí duyệt -> Đã phê duyệt hoặc Yêu cầu chỉnh sửa. Chặn sửa khi đang chờ hoặc đã phê duyệt.
17. Chuẩn hóa trạng thái kế hoạch cũ “Đã duyệt” thành “Đã phê duyệt”.
18. Sửa UI Khảo thí gửi đúng giá trị “Yêu cầu chỉnh sửa” thay vì “Từ chối”.
19. Tổng hợp Hội đồng/Thư ký/Khảo thí có thể lọc theo kế hoạch để không trộn nhiều chu kỳ tự đánh giá.
20. Sửa filter Minh chứng Admin từ “Chờ duyệt” chung sang đúng “Chờ Trưởng nhóm duyệt” và “Chờ Thư ký duyệt”.
21. Sửa phiên bản CTĐT: backend chấp nhận cả năm 2026 và năm học 2024-2025 giống UI.
22. Bổ sung ràng buộc quan hệ: không xóa Bộ tiêu chuẩn/Tiêu chuẩn/Tiêu chí khi đang được tham chiếu bởi kế hoạch, phân công, minh chứng, báo cáo hoặc đánh giá; không cho chuyển Tiêu chuẩn/Tiêu chí sang cha khác khi sửa.
23. Sửa migration khởi động: phân công Tiêu chuẩn trùng được xác định theo cả kế hoạch, tránh tự hủy phân công ở chu kỳ khác sau restart.
24. Sửa lựa chọn vai trò chính khi tài khoản có nhiều vai trò để bao phủ Admin/Hội đồng/Trưởng nhóm/Thư ký/Khảo thí/Thành viên.

## Kiểm tra tĩnh đã thực hiện

- Kiểm tra cú pháp JavaScript inline của toàn bộ template bằng `node --check`: không phát hiện lỗi cú pháp.
- Kiểm tra tất cả route controller: không phát hiện mapping trùng method + URL.
- Kiểm tra cú pháp Java bằng `javac` ở mức parser: không phát hiện lỗi cú pháp; môi trường không có Maven/dependency Spring nên không chạy được full `mvn test/package`.

## Luồng nghiệp vụ sau khi chỉnh

Thư ký lập kế hoạch -> Khảo thí phê duyệt -> Admin giao Tiêu chuẩn cho Trưởng nhóm -> Trưởng nhóm giao Tiêu chí -> Thành viên/Nhân sự hỗ trợ nộp minh chứng -> Trưởng nhóm duyệt -> Thư ký duyệt -> Thành viên hoàn thiện và gửi báo cáo -> Hội đồng duyệt/ yêu cầu chỉnh sửa -> Hội đồng đánh giá Tiêu chí -> Thư ký/Hội đồng/Khảo thí tổng hợp theo kế hoạch.
