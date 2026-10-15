package com.huit.kiemdinh.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Locale;
import java.util.Optional;

@Service
public class FileStorageService {
    private final Path root;

    public FileStorageService(@Value("${app.storage.root}") String rootPath) {
        this.root = Path.of(rootPath).toAbsolutePath().normalize();
    }

    public Path getRoot() { return root; }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(root.resolve("ctdt"));
        Files.createDirectories(root.resolve("minhchung"));
        Files.createDirectories(root.resolve("minh-chung-chung"));
        Files.createDirectories(root.resolve("bao-cao"));
        migrateLegacyUploads();
    }

    private void migrateLegacyUploads() {
        Path legacy = Path.of("uploads").toAbsolutePath().normalize();
        if (legacy.equals(root) || !Files.exists(legacy)) return;
        for (String sub : new String[]{"ctdt", "minhchung", "minh-chung-chung", "bao-cao"}) {
            Path from = legacy.resolve(sub);
            Path to = root.resolve(sub);
            if (!Files.exists(from)) continue;
            try {
                Files.createDirectories(to);
                try (var stream = Files.list(from)) {
                    stream.filter(Files::isRegularFile).forEach(src -> {
                        Path dst = to.resolve(src.getFileName().toString());
                        if (!Files.exists(dst)) {
                            try { Files.copy(src, dst); } catch (IOException ignored) {}
                        }
                    });
                }
            } catch (IOException ignored) {}
        }
    }

    public String saveCtdt(String id, String maPhienBan, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("ctdt.pdf");
        String ext = extension(original);
        String safe = sanitize(id + "_" + maPhienBan + "_" + System.currentTimeMillis() + ext);
        Path dir = root.resolve("ctdt");
        Files.createDirectories(dir);
        Files.copy(file.getInputStream(), dir.resolve(safe), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/ctdt/" + safe;
    }

    public String saveMinhChung(String id, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("minhchung.bin");
        String ext = extension(original).toLowerCase(Locale.ROOT);
        String safe = sanitize(id + "_" + System.currentTimeMillis() + ext);
        Path dir = root.resolve("minhchung");
        Files.createDirectories(dir);
        Files.copy(file.getInputStream(), dir.resolve(safe), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/minhchung/" + safe;
    }


    public String saveMinhChungChung(String id, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("minhchungchung.bin");
        String ext = extension(original).toLowerCase(Locale.ROOT);
        String safe = sanitize(id + "_" + System.currentTimeMillis() + ext);
        Path dir = root.resolve("minh-chung-chung");
        Files.createDirectories(dir);
        Files.copy(file.getInputStream(), dir.resolve(safe), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/minh-chung-chung/" + safe;
    }


    public String saveBaoCao(String id, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("baocao.pdf");
        String ext = extension(original).toLowerCase(Locale.ROOT);
        String safe = sanitize(id + "_" + System.currentTimeMillis() + ext);
        Path dir = root.resolve("bao-cao");
        Files.createDirectories(dir);
        Files.copy(file.getInputStream(), dir.resolve(safe), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/bao-cao/" + safe;
    }

    public void deleteByWebPath(String webPath) {
        if (webPath == null || webPath.isBlank() || !webPath.startsWith("/uploads/")) return;
        String relative = webPath.substring("/uploads/".length());
        if (relative.contains("..")) return;
        Path target = root.resolve(relative).normalize();
        if (!target.startsWith(root)) return;
        try { Files.deleteIfExists(target); } catch (IOException ignored) {}
    }

    private String extension(String filename) {
        int p = filename.lastIndexOf('.');
        return p >= 0 ? filename.substring(p) : "";
    }

    private String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
