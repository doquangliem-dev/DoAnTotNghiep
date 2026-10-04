# Kho file upload

Từ HeThongKiemDinh24, file thực tế được lưu bền vững ngoài source code tại:

- Windows mặc định: `C:\Users\<user>\HeThongKiemDinhStorage`
- CTĐT: `HeThongKiemDinhStorage\ctdt`
- Minh chứng: `HeThongKiemDinhStorage\minhchung`

Có thể đổi vị trí bằng biến môi trường `APP_STORAGE_ROOT`.
Thư mục này không nên xóa nếu muốn giữ các file đã upload giữa các lần khởi động/build/project version.
