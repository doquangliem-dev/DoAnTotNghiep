package com.huit.kiemdinh.security;

import com.huit.kiemdinh.model.NguoiDung;
import com.huit.kiemdinh.model.PhanQuyen;
import com.huit.kiemdinh.model.VaiTro;
import com.huit.kiemdinh.repository.NguoiDungRepository;
import com.huit.kiemdinh.repository.VaiTroRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class AuthService {
    public static final String SESSION_USER_ID = "AUTH_USER_ID";
    public static final String SESSION_EMAIL = "AUTH_EMAIL";
    public static final String SESSION_NAME = "AUTH_NAME";
    public static final String SESSION_ROLE_IDS = "AUTH_ROLE_IDS";
    public static final String SESSION_ROLE_NAMES = "AUTH_ROLE_NAMES";
    public static final String SESSION_PRIMARY_ROLE = "AUTH_PRIMARY_ROLE";

    private final NguoiDungRepository userRepo;
    private final VaiTroRepository roleRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(NguoiDungRepository userRepo, VaiTroRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public Map<String,Object> login(String email, String password, HttpSession session) {
        if(email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vui lòng nhập email và mật khẩu");
        }

        NguoiDung user = userRepo.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng"));

        if("Khóa".equalsIgnoreCase(user.getTrangThai())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tài khoản đang bị khóa");
        }

        String stored = Optional.ofNullable(user.getMatKhau()).orElse("");
        boolean matched = false;
        if(stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
            matched = encoder.matches(password, stored);
        } else {
            // Hỗ trợ dữ liệu cũ đang lưu mật khẩu thường; đăng nhập thành công sẽ tự nâng cấp sang BCrypt.
            matched = stored.equals(password);
            if(matched) {
                user.setMatKhau(encoder.encode(password));
                userRepo.save(user);
            }
        }

        if(!matched) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng");
        }

        List<String> roleIds = new ArrayList<>();
        if(user.getPhanQuyen() != null) {
            for(PhanQuyen pq : user.getPhanQuyen()) {
                if(pq != null && pq.getMaVaiTro() != null && !pq.getMaVaiTro().isBlank()) roleIds.add(pq.getMaVaiTro());
            }
        }

        Map<String,String> roleNameById = new HashMap<>();
        for(VaiTro v : roleRepo.findAll()) roleNameById.put(v.getId(), v.getTenVaiTro());
        List<String> roleNames = roleIds.stream().map(id -> roleNameById.getOrDefault(id, id)).toList();
        String primaryRole = choosePrimaryRole(roleNames);

        session.setAttribute(SESSION_USER_ID, user.getId());
        session.setAttribute(SESSION_EMAIL, user.getEmail());
        session.setAttribute(SESSION_NAME, user.getHoTen());
        session.setAttribute(SESSION_ROLE_IDS, roleIds);
        session.setAttribute(SESSION_ROLE_NAMES, roleNames);
        session.setAttribute(SESSION_PRIMARY_ROLE, primaryRole);

        return me(session);
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public Map<String,Object> me(HttpSession session) {
        String id = (String) session.getAttribute(SESSION_USER_ID);
        if(id == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Chưa đăng nhập");
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("hoTen", session.getAttribute(SESSION_NAME));
        m.put("email", session.getAttribute(SESSION_EMAIL));
        m.put("vaiTro", session.getAttribute(SESSION_PRIMARY_ROLE));
        m.put("vaiTroIds", session.getAttribute(SESSION_ROLE_IDS));
        m.put("vaiTroNames", session.getAttribute(SESSION_ROLE_NAMES));
        return m;
    }

    public boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(SESSION_USER_ID) != null;
    }

    public boolean hasRole(HttpSession session, String keyword) {
        Object o = session.getAttribute(SESSION_ROLE_NAMES);
        if(!(o instanceof List<?> list)) return false;
        String k = normalize(keyword);
        return list.stream().filter(Objects::nonNull).map(String::valueOf).map(this::normalize).anyMatch(x -> x.contains(k));
    }


    public String homeFor(HttpSession session) {
        if(hasRole(session, "Quản trị")) return "/admin";
        if(hasRole(session, "Hội đồng")) return "/hoi-dong";
        if(hasRole(session, "Trưởng nhóm")) return "/truong-nhom";
        if(hasRole(session, "Thành viên")) return "/thanh-vien";
        if(hasRole(session, "Thư ký")) return "/thu-ky";
        if(hasRole(session, "Khảo thí")) return "/khao-thi";
        if(hasRole(session, "Nhân sự phụ trách báo cáo")) return "/nhan-su-bao-cao";
        return "/login";
    }

    public String encodePassword(String raw) {
        return encoder.encode(raw);
    }

    public boolean isEncoded(String value) {
        return value != null && (value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$"));
    }

    private String choosePrimaryRole(List<String> names) {
        // Thứ tự ưu tiên chỉ dùng để chọn giao diện mặc định khi một tài khoản có nhiều vai trò.
        String[] priority={"quan tri","hoi dong","truong nhom","thu ky chuong trinh","khao thi","nhan su phu trach bao cao","thanh vien"};
        for(String key:priority) for(String n:names) if(normalize(n).contains(key)) return n;
        return names.isEmpty() ? "Chưa phân vai trò" : names.get(0);
    }

    private String normalize(String s) {
        if(s == null) return "";
        String n = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace('đ','d').replace('Đ','D')
                .toLowerCase(Locale.ROOT);
        return n;
    }
}
