package com.huit.kiemdinh.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.net.URI;

import java.net.URLEncoder;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.nio.file.*;

import java.time.Duration;

import java.util.Locale;

import java.util.Optional;

@Service
public class FileStorageService {
    private final Path root;
    private final String mode;
    private final String supabaseUrl;
    private final String supabaseServiceRoleKey;
    private final String supabaseBucket;
    private final Path seedRoot;
    private final HttpClient httpClient = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(10))
    .followRedirects(HttpClient.Redirect.NORMAL).build();
    public FileStorageService(@Value("${app.storage.root}") String rootPath,
    @Value("${app.storage.mode:local}") String mode,
    @Value("${app.storage.supabase.url:}") String supabaseUrl,
    @Value("${app.storage.supabase.service-role-key:}") String supabaseServiceRoleKey,
    @Value("${app.storage.supabase.bucket:hethongkiemdinh}") String supabaseBucket,
    @Value("${app.storage.seed-root:storage-seed}") String seedRootPath) {
        this.root = Path.of(rootPath).toAbsolutePath().normalize();
        this.mode = mode == null ? "local" : mode.trim().toLowerCase(Locale.ROOT);
        this.supabaseUrl = trimSlash(supabaseUrl);
        this.supabaseServiceRoleKey = supabaseServiceRoleKey == null ? "" : supabaseServiceRoleKey.trim();
        this.supabaseBucket = supabaseBucket == null || supabaseBucket.isBlank() ? "hethongkiemdinh" : supabaseBucket.trim();
        this.seedRoot = Path.of(seedRootPath).toAbsolutePath().normalize();
    }
    public Path getRoot() {
        return root;
    }
    public boolean isCloud() {
        return "supabase".equals(mode);
    }
    @PostConstruct
    public void init() throws IOException {
        if (isCloud()) {
            if (supabaseUrl.isBlank() || supabaseServiceRoleKey.isBlank()) {
                throw new IllegalStateException("APP_STORAGE_MODE=supabase nhưng thiếu SUPABASE_URL hoặc SUPABASE_SERVICE_ROLE_KEY");
            }
            Thread syncThread = new Thread(this::syncSeedFilesToSupabase, "supabase-seed-sync");
            syncThread.setDaemon(true);
            syncThread.start();
            return;
        }
        Files.createDirectories(root.resolve("ctdt"));
        Files.createDirectories(root.resolve("minhchung"));
        Files.createDirectories(root.resolve("minh-chung-chung"));
        Files.createDirectories(root.resolve("bao-cao"));
        seedBundledFiles();
        migrateLegacyUploads();
    }
    private void syncSeedFilesToSupabase() {
        if (!Files.isDirectory(seedRoot)) return;
        for (String sub : new String[] {
            "ctdt", "minhchung", "minh-chung-chung", "bao-cao"
        }
        ) {
            Path from = seedRoot.resolve(sub);
            if (!Files.isDirectory(from)) continue;
            try (var stream = Files.walk(from)) {
                stream.filter(Files::isRegularFile).forEach(src -> {
                    Path relPath = seedRoot.relativize(src);
                    String relative = relPath.toString().replace('\\', '/');
                    try {
                        // Luôn upsert file seed để bản sửa mới ghi đè các PDF seed cũ đã có trên Supabase.
                        // File người dùng upload có tên timestamp riêng nên không bị ảnh hưởng.
                        uploadSupabaseBytes(relative, Files.readAllBytes(src), contentType(relative));
                        System.out.println("[Storage] Đã đồng bộ/cập nhật seed lên Supabase: " + relative);
                    } catch (Exception e) {
                        System.err.println("[Storage] Không thể đồng bộ seed " + relative + ": " + e.getMessage());
                    }
                }
                );
            } catch (IOException e) {
                System.err.println("[Storage] Không thể đọc thư mục seed " + sub + ": " + e.getMessage());
            }
        }
    }
    private boolean supabaseObjectExists(String relative) throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder(objectInfoUri(relative))
            .timeout(Duration.ofSeconds(25))
            .header("Authorization", "Bearer " + supabaseServiceRoleKey)
            .header("apikey", supabaseServiceRoleKey)
            .method("HEAD", HttpRequest.BodyPublishers.noBody())
            .build();
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() >= 200 && response.statusCode() < 300) return true;
            if (response.statusCode() == 404 || response.statusCode() == 400) return false;
            throw new IOException("Supabase kiểm tra file thất bại (HTTP " + response.statusCode() + ")");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Kiểm tra file Supabase bị gián đoạn", e);
        }
    }
    private void uploadSupabaseBytes(String relative, byte[] bytes, String contentType) throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder(objectUri(relative, false))
            .timeout(Duration.ofSeconds(30))
            .header("Authorization", "Bearer " + supabaseServiceRoleKey)
            .header("apikey", supabaseServiceRoleKey)
            .header("Content-Type", contentType)
            .header("x-upsert", "true")
            .POST(HttpRequest.BodyPublishers.ofByteArray(bytes))
            .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException("Supabase upload seed thất bại (HTTP " + response.statusCode() + "): " + response.body());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Upload seed bị gián đoạn", e);
        }
    }
    private void seedBundledFiles() {
        if (!Files.isDirectory(seedRoot)) return;
        for (String sub : new String[] {
            "ctdt", "minhchung", "minh-chung-chung", "bao-cao"
        }
        ) {
            Path from = seedRoot.resolve(sub);
            Path to = root.resolve(sub);
            if (!Files.isDirectory(from)) continue;
            try {
                Files.createDirectories(to);
                try (var stream = Files.walk(from)) {
                    stream.filter(Files::isRegularFile).forEach(src -> {
                        Path relative = from.relativize(src);
                        Path dst = to.resolve(relative).normalize();
                        if (!dst.startsWith(to)) return;
                        try {
                            Files.createDirectories(dst.getParent());
                            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ignored) {
                        }
                    }
                    );
                }
            } catch (IOException ignored) {
            }
        }
    }
    private void migrateLegacyUploads() {
        Path legacy = Path.of("uploads").toAbsolutePath().normalize();
        if (legacy.equals(root) || !Files.exists(legacy)) return;
        for (String sub : new String[] {
            "ctdt", "minhchung", "minh-chung-chung", "bao-cao"
        }
        ) {
            Path from = legacy.resolve(sub);
            Path to = root.resolve(sub);
            if (!Files.exists(from)) continue;
            try {
                Files.createDirectories(to);
                try (var stream = Files.list(from)) {
                    stream.filter(Files::isRegularFile).forEach(src -> {
                        Path dst = to.resolve(src.getFileName().toString());
                        if (!Files.exists(dst)) {
                            try {
                                Files.copy(src, dst);
                            } catch (IOException ignored) {
                            }
                        }
                    }
                    );
                }
            } catch (IOException ignored) {
            }
        }
    }
    public String saveCtdt(String id, String maPhienBan, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("ctdt.pdf");
        String safe = sanitize(id + "_" + maPhienBan + "_" + System.currentTimeMillis() + extension(original));
        return save("ctdt/" + safe, file);
    }
    public String saveMinhChung(String id, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("minhchung.bin");
        String safe = sanitize(id + "_" + System.currentTimeMillis() + extension(original).toLowerCase(Locale.ROOT));
        return save("minhchung/" + safe, file);
    }
    public String saveMinhChungChung(String id, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("minhchungchung.bin");
        String safe = sanitize(id + "_" + System.currentTimeMillis() + extension(original).toLowerCase(Locale.ROOT));
        return save("minh-chung-chung/" + safe, file);
    }
    public String saveBaoCao(String id, MultipartFile file) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("baocao.pdf");
        String safe = sanitize(id + "_" + System.currentTimeMillis() + extension(original).toLowerCase(Locale.ROOT));
        return save("bao-cao/" + safe, file);
    }
    private String save(String relative, MultipartFile file) throws IOException {
        validateRelative(relative);
        if (isCloud()) {
            uploadSupabase(relative, file);
        } else {
            Path target = root.resolve(relative).normalize();
            if (!target.startsWith(root)) throw new IOException("Đường dẫn file không hợp lệ");
            Files.createDirectories(target.getParent());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        }
        return "/uploads/" + relative;
    }
    public StoredFile readByWebPath(String webPath) throws IOException {
        String relative = relativeFromWebPath(webPath);
        String effectiveRelative = relative;
        byte[] data;
        if (isCloud()) {
            try {
                data = downloadSupabase(effectiveRelative);
            } catch (IOException cloudError) {
                Path seeded = seedRoot.resolve(effectiveRelative).normalize();
                if (seeded.startsWith(seedRoot) && Files.isRegularFile(seeded)) {
                    data = Files.readAllBytes(seeded);
                } else {
                    throw cloudError;
                }
            }
        } else {
            Path target = root.resolve(effectiveRelative).normalize();
            if (!target.startsWith(root) || !Files.isRegularFile(target)) throw new NoSuchFileException(effectiveRelative);
            data = Files.readAllBytes(target);
        }
        return new StoredFile(data, contentType(effectiveRelative));
    }
    public void deleteByWebPath(String webPath) {
        if (webPath == null || webPath.isBlank()) return;
        try {
            String relative = relativeFromWebPath(webPath);
            if (isCloud()) {
                deleteSupabase(relative);
                return;
            }
            Path target = root.resolve(relative).normalize();
            if (!target.startsWith(root)) return;
            Files.deleteIfExists(target);
        } catch (IOException ignored) {
        }
    }
    private void uploadSupabase(String relative, MultipartFile file) throws IOException {
        try {
            String contentType = Optional.ofNullable(file.getContentType()).filter(s -> !s.isBlank()).orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            HttpRequest request = HttpRequest.newBuilder(objectUri(relative, false))
            .timeout(Duration.ofSeconds(30))
            .header("Authorization", "Bearer " + supabaseServiceRoleKey)
            .header("apikey", supabaseServiceRoleKey)
            .header("Content-Type", contentType)
            .header("x-upsert", "true")
            .POST(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
            .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException("Supabase upload thất bại (HTTP " + response.statusCode() + "): " + response.body());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Upload bị gián đoạn", e);
        }
    }
    private byte[] downloadSupabase(String relative) throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder(objectUri(relative, true))
            .timeout(Duration.ofSeconds(30))
            .header("Authorization", "Bearer " + supabaseServiceRoleKey)
            .header("apikey", supabaseServiceRoleKey)
            .GET().build();
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() == 404) throw new NoSuchFileException(relative);
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException("Supabase download thất bại (HTTP " + response.statusCode() + ")");
            }
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Download bị gián đoạn", e);
        }
    }
    private void deleteSupabase(String relative) throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder(objectUri(relative, false))
            .timeout(Duration.ofSeconds(30))
            .header("Authorization", "Bearer " + supabaseServiceRoleKey)
            .header("apikey", supabaseServiceRoleKey)
            .DELETE().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 404 && (response.statusCode() < 200 || response.statusCode() >= 300)) {
                throw new IOException("Supabase delete thất bại (HTTP " + response.statusCode() + "): " + response.body());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Xóa file bị gián đoạn", e);
        }
    }
    private URI objectUri(String relative, boolean authenticatedDownload) {
        String encodedBucket = encodeSegment(supabaseBucket);
        String encodedPath = encodePath(relative);
        String prefix = authenticatedDownload ? "/storage/v1/object/authenticated/" : "/storage/v1/object/";
        return URI.create(supabaseUrl + prefix + encodedBucket + "/" + encodedPath);
    }
    private URI objectInfoUri(String relative) {
        String encodedBucket = encodeSegment(supabaseBucket);
        String encodedPath = encodePath(relative);
        return URI.create(supabaseUrl + "/storage/v1/object/info/" + encodedBucket + "/" + encodedPath);
    }
    private String relativeFromWebPath(String webPath) throws IOException {
        if (webPath == null || !webPath.startsWith("/uploads/")) throw new IOException("Đường dẫn file không hợp lệ");
        String relative = webPath.substring("/uploads/".length());
        validateRelative(relative);
        return relative;
    }
    private void validateRelative(String relative) throws IOException {
        if (relative == null || relative.isBlank() || relative.startsWith("/") || relative.contains("..") || relative.contains("\\")) {
            throw new IOException("Đường dẫn file không hợp lệ");
        }
    }
    private String extension(String filename) {
        int p = filename.lastIndexOf('.');
        return p >= 0 ? filename.substring(p) : "";
    }
    private String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
    private String contentType(String relative) {
        String p = relative.toLowerCase(Locale.ROOT);
        if (p.endsWith(".pdf")) return "application/pdf";
        if (p.endsWith(".doc")) return "application/msword";
        if (p.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (p.endsWith(".xls")) return "application/vnd.ms-excel";
        if (p.endsWith(".xlsx")) return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (p.endsWith(".png")) return "image/png";
        if (p.endsWith(".jpg") || p.endsWith(".jpeg")) return "image/jpeg";
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
    private static String encodeSegment(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }
    private static String encodePath(String path) {
        StringBuilder b = new StringBuilder();
        for (String segment : path.split("/")) {
            if (!b.isEmpty()) b.append('/');
            b.append(encodeSegment(segment));
        }
        return b.toString();
    }
    private static String trimSlash(String value) {
        if (value == null) return "";
        String s = value.trim();
        while (s.endsWith("/")) s = s.substring(0, s.length() - 1);
        return s;
    }
    public record StoredFile(byte[] data, String contentType) {
    }
}
