# Bộ file thật phục vụ kiểm thử

Project có thư mục `storage-seed/` chứa các PDF được tách/ghép từ tài liệu thật trong bộ hồ sơ người dùng cung cấp.

Khi chạy Docker ở chế độ local, `FileStorageService` tự copy các file còn thiếu từ `/app/storage-seed` vào named volume `/app/storage`. File đã tồn tại trong volume sẽ không bị ghi đè.

- `ctdt/`: file CTĐT thật.
- `minhchung/`: 52 gói minh chứng tham chiếu cho 52 tiêu chí.
- `minh-chung-chung/`: 10 gói minh chứng cấp Trường/Khoa.
- `bao-cao/`: 52 báo cáo tiêu chí, 8 báo cáo nhóm, Phần 1/3/4.

Mongo có hai lựa chọn:

1. Chạy `mongo/RESET_MONGO_THEO_EXCEL.txt` để drop và dựng lại toàn bộ dữ liệu kèm đường dẫn file thật.
2. Nếu không muốn drop dữ liệu hiện tại, chạy `mongo/PATCH_FILE_THAT.txt` để chỉ cập nhật đường dẫn file.

Sau khi chạy Docker, kiểm tra:

```powershell
docker exec -it hethongkiemdinh sh
ls -R /app/storage
```
