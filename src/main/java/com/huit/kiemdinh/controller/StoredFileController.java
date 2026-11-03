package com.huit.kiemdinh.controller;

import com.huit.kiemdinh.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.NoSuchFileException;
import java.util.concurrent.TimeUnit;

@RestController
public class StoredFileController {
    private final FileStorageService storage;

    public StoredFileController(FileStorageService storage) {
        this.storage = storage;
    }

    @GetMapping("/uploads/**")
    public ResponseEntity<byte[]> getFile(HttpServletRequest request) throws Exception {
        String path = request.getRequestURI();
        try {
            FileStorageService.StoredFile file = storage.readByWebPath(path);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.contentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES).cachePrivate())
                    .body(file.data());
        } catch (NoSuchFileException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
