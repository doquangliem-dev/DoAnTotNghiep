package com.huit.kiemdinh.controller;
import com.huit.kiemdinh.model.*;
import com.huit.kiemdinh.security.AuthService;
import com.huit.kiemdinh.service.FileStorageService;
import com.huit.kiemdinh.service.HeThongService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.concurrent.TimeUnit;
@RestController
public class StoredFileController {
    private final FileStorageService storage;
    private final HeThongService service;
    private final AuthService auth;
    public StoredFileController(FileStorageService storage, HeThongService service, AuthService auth) {
        this.storage=storage; this.service=service; this.auth=auth;
    }
    @GetMapping("/uploads/**")
    public ResponseEntity<byte[]> getFile(HttpServletRequest request, HttpSession session) throws Exception {
        String path=request.getRequestURI();
        if(!canRead(path,session)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không có quyền xem tệp này");
        try {
            FileStorageService.StoredFile file=storage.readByWebPath(path);
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline")
                .cacheControl(CacheControl.maxAge(5,TimeUnit.MINUTES).cachePrivate()).body(file.data());
        } catch(NoSuchFileException e) { return ResponseEntity.notFound().build(); }
    }
    private boolean canRead(String path,HttpSession session) {
        if(!auth.isLoggedIn(session)) return false;
        if(auth.hasRole(session,"Quản trị") || auth.hasRole(session,"Hội đồng")
        || auth.hasRole(session,"Thư ký") || auth.hasRoleId(session,"VT006")
        || auth.hasRole(session,"Trung tâm Quản lý chất lượng") || auth.hasRole(session,"Quản lý chất lượng")
        || auth.hasRole(session,"Bộ phận khảo thí") || auth.hasRole(session,"Khảo thí")) return true;
        String uid=(String)session.getAttribute(AuthService.SESSION_USER_ID);
        // Trưởng nhóm và Thành viên được QUYỀN XEM/TẢI minh chứng dùng chung cấp Trường/Khoa.
        // Quyền ghi vẫn bị khóa ở ApiController: chỉ Quản trị được thêm, sửa, upload hoặc xóa.
        boolean readSharedEvidence = auth.hasRole(session,"Trưởng nhóm") || auth.hasRole(session,"Thành viên");
        if(readSharedEvidence && service.minhChungChung().stream().anyMatch(x ->
            path.equals(x.getDuongDanFile()) || path.equals(x.getDuongDanTaiLieuThamChieu()))) return true;
        if(auth.hasRole(session,"Trưởng nhóm")) {
            if(service.minhChungTruongNhom(uid).stream().anyMatch(x->path.equals(x.getDuongDanFile()))) return true;
            if(service.baoCaoTruongNhom(uid).stream().anyMatch(x->path.equals(x.getDuongDanFile()))) return true;
            if(service.baoCaoNhomCuaTruongNhom(uid).stream().anyMatch(x->path.equals(x.getDuongDanFile()))) return true;
        }
        if(auth.hasRole(session,"Thành viên")) {
            if(service.minhChungThanhVien(uid).stream().anyMatch(x->path.equals(x.getDuongDanFile()))) return true;
            if(service.baoCaoCuaNguoiDung(uid).stream().anyMatch(x->path.equals(x.getDuongDanFile()))) return true;
            if(service.baoCaoNhomCuaThuKy(uid).stream().anyMatch(x->path.equals(x.getDuongDanFile()))) return true;
        }
        if(auth.hasRole(session,"Nhân sự phụ trách báo cáo")
        && service.baoCaoPhanCuaNguoiDung(uid).stream().anyMatch(x->path.equals(x.getDuongDanFile()))) return true;
        return false;
    }
}
