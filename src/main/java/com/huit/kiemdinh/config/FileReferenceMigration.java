package com.huit.kiemdinh.config;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
* Giữ nguyên đường dẫn file logic kiểu cũ trong Mongo.
* FileStorageService sẽ ánh xạ các file seed mẫu sang tài liệu đầy đủ khi đọc.
* File người dùng upload có timestamp nên luôn được đọc trực tiếp.
*/
@Component
public class FileReferenceMigration {
    @PostConstruct
    public void migrateSeedFileReferences() {
        // Cố ý không thay đổi dữ liệu Mongo.
    }
}
