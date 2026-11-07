# Data/File hoàn chỉnh - 2026-11-07

- Giữ nguyên file CTĐT, không sinh CTĐT giả.
- 52 minh chứng tiêu chí: mỗi tiêu chí có 1 PDF riêng, đầy đủ cấu trúc và bảng kiểm.
- 10 minh chứng dùng chung: mỗi minh chứng có 1 PDF riêng.
- 52 báo cáo tiêu chí: mỗi tiêu chí có 1 PDF riêng, gồm mô tả hiện trạng, điểm mạnh, tồn tại, kế hoạch hành động và phụ lục đối chiếu.
- 8 báo cáo nhóm: mỗi tiêu chuẩn có 1 PDF tổng hợp riêng.
- Phần 1, 3, 4: mỗi phần có PDF riêng.
- `DataCompletenessRunner` chỉ bổ sung trường đang thiếu/generic, không thay nội dung người dùng đã nhập đầy đủ.
- `FileDataRepairRunner` sửa đường dẫn seed cũ/null nhưng không ghi đè đường dẫn file người dùng upload có timestamp.
- Khi chạy Supabase, file seed được upsert nền; web vẫn có fallback đọc `storage-seed` nếu cloud chưa đồng bộ xong.

Nếu cần đồng bộ Mongo thủ công, chạy `mongo/PATCH_DATA_FILE_HOAN_CHINH.txt` trong MONGOSH.
Sau đó chạy `mongo/KIEM_TRA_DATA_FILE_HOAN_CHINH.txt` để kiểm tra.
