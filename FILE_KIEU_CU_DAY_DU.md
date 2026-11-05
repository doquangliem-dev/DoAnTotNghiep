# File kiểu cũ nhưng dùng tài liệu đầy đủ

- Mongo và giao diện vẫn dùng tên/URL riêng như `MC001_TC1.1.pdf`, `BC001_TC1.1.pdf`, `BCN001_TC1.pdf`.
- Các URL seed mẫu được backend ánh xạ sang tài liệu đầy đủ để tránh mở bản PDF cắt thiếu còn sót trong Docker volume.
- Báo cáo tiêu chí và báo cáo nhóm mẫu mở toàn bộ Phần 2.
- Phần 1, 3, 4 mở đúng file đầy đủ của từng phần.
- Minh chứng mẫu mở toàn bộ danh mục minh chứng 75 trang. Khi người dùng upload file thật (tên có timestamp), backend đọc đúng file upload riêng và không ánh xạ.
