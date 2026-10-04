package com.huit.kiemdinh.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService auth;
    public AuthInterceptor(AuthService auth){ this.auth = auth; }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession(false);
        String uri=request.getRequestURI();
        if(session==null || !auth.isLoggedIn(session)){
            if(uri.startsWith("/api/")){
                response.setStatus(401); response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"message\":\"Phiên đăng nhập đã hết hạn\"}");
            }else response.sendRedirect("/login");
            return false;
        }

        // Bảo vệ khu vực giao diện theo vai trò.
        if(uri.startsWith("/admin") && !hasAny(session,"Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/hoi-dong") && !hasAny(session,"Hội đồng","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/truong-nhom") && !hasAny(session,"Trưởng nhóm","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/thanh-vien") && !hasAny(session,"Thành viên","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/thu-ky") && !hasAny(session,"Thư ký","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/khao-thi") && !hasAny(session,"Khảo thí","Quản trị")) return deny(request,response,auth.homeFor(session));

        // API theo vai trò: kiểm tra ở backend, không dựa vào việc ẩn/hiện nút trên giao diện.
        if(uri.startsWith("/api/hoi-dong/") && !hasAny(session,"Hội đồng","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/truong-nhom/") && !hasAny(session,"Trưởng nhóm","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/thanh-vien/") && !hasAny(session,"Thành viên","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/thu-ky/") && !hasAny(session,"Thư ký","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/khao-thi/") && !hasAny(session,"Khảo thí","Quản trị")) return deny(request,response,auth.homeFor(session));

        // Các API dùng chung có thao tác ghi dữ liệu quản trị phải là Admin.
        // Các vai trò nghiệp vụ phải dùng API /api/<vai-tro>/... tương ứng để service kiểm tra quyền sở hữu/phân công.
        if(isAdminWriteApi(uri,request.getMethod()) && !hasAny(session,"Quản trị"))
            return deny(request,response,auth.homeFor(session));

        // API dữ liệu toàn hệ thống chỉ dành cho Admin; các vai trò nghiệp vụ dùng endpoint đã giới hạn phạm vi riêng.
        if(isAdminOnlyApi(uri) && !hasAny(session,"Quản trị"))
            return deny(request,response,auth.homeFor(session));

        return true;
    }

    private boolean isAdminWriteApi(String uri,String method){
        if("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method) || "OPTIONS".equalsIgnoreCase(method)) return false;
        List<String> prefixes=List.of(
                "/api/ctdt",
                "/api/bo-tieu-chuan",
                "/api/tieu-chuan",
                "/api/tieu-chi",
                "/api/nguoi-dung",
                "/api/nhom-tieu-chi",
                "/api/phan-cong",
                "/api/minh-chung-chung",
                "/api/minh-chung",
                "/api/bao-cao"
        );
        return prefixes.stream().anyMatch(uri::startsWith);
    }


    private boolean isAdminOnlyApi(String uri){
        if("/api/dashboard".equals(uri)) return true;
        return uri.equals("/api/phan-cong") || uri.startsWith("/api/phan-cong/")
                || uri.equals("/api/minh-chung") || uri.startsWith("/api/minh-chung/")
                || uri.equals("/api/bao-cao") || uri.startsWith("/api/bao-cao/");
    }

    private boolean hasAny(HttpSession session,String... roles){
        for(String role:roles) if(auth.hasRole(session,role)) return true;
        return false;
    }

    private boolean deny(HttpServletRequest req,HttpServletResponse res,String home) throws Exception{
        if(req.getRequestURI().startsWith("/api/")){
            res.setStatus(403); res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write("{\"message\":\"Bạn không có quyền sử dụng chức năng này\"}");
        }else res.sendRedirect(home);
        return false;
    }
}
