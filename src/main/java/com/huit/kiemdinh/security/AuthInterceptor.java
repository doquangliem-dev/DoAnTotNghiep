package com.huit.kiemdinh.security;
import com.huit.kiemdinh.config.DemoDataState;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.List;
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService auth;
    private final DemoDataState dataState;
    public AuthInterceptor(AuthService auth, DemoDataState dataState) {
        this.auth = auth;
        this.dataState = dataState;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession(false);
        String uri=request.getRequestURI();
        if(session==null || !auth.isLoggedIn(session)) {
            if(uri.startsWith("/api/")) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"message\":\"Phiên đăng nhập đã hết hạn\"}");
            } else response.sendRedirect("/login");
            return false;
        }
        // ApplicationRunner seed chạy sau khi Tomcat đã mở cổng. Chặn API nghiệp vụ
        // trong vài giây seed để giao diện không đọc trúng collection đang được tạo lại.
        if(uri.startsWith("/api/") && !uri.startsWith("/api/auth/") && !dataState.isReady()) {
            response.setStatus(503);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"Hệ thống đang chuẩn bị dữ liệu, vui lòng chờ vài giây\"}");
            return false;
        }
        // Bảo vệ khu vực giao diện theo vai trò.
        if(uri.startsWith("/admin") && !hasAny(session,"Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/hoi-dong") && !hasAny(session,"Hội đồng","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/truong-nhom") && !hasAny(session,"Trưởng nhóm","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/thanh-vien") && !hasAny(session,"Thành viên","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/thu-ky") && !hasAny(session,"Thư ký","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/khao-thi") && !(auth.hasRoleId(session,"VT006") || hasAny(session,"Trung tâm Quản lý chất lượng","Quản lý chất lượng","Bộ phận khảo thí","Khảo thí","Quản trị"))) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/nhan-su-bao-cao") && !hasAny(session,"Nhân sự phụ trách báo cáo","Quản trị")) return deny(request,response,auth.homeFor(session));
        // API theo vai trò: kiểm tra ở backend, không dựa vào việc ẩn/hiện nút trên giao diện.
        if(uri.startsWith("/api/admin/") && !hasAny(session,"Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/hoi-dong/") && !hasAny(session,"Hội đồng","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/truong-nhom/") && !hasAny(session,"Trưởng nhóm","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/thanh-vien/") && !hasAny(session,"Thành viên","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/thu-ky/") && !hasAny(session,"Thư ký","Quản trị")) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/khao-thi/") && !(auth.hasRoleId(session,"VT006") || hasAny(session,"Trung tâm Quản lý chất lượng","Quản lý chất lượng","Bộ phận khảo thí","Khảo thí","Quản trị"))) return deny(request,response,auth.homeFor(session));
        if(uri.startsWith("/api/nhan-su-bao-cao/") && !hasAny(session,"Nhân sự phụ trách báo cáo","Quản trị")) return deny(request,response,auth.homeFor(session));
        // Các API dùng chung có thao tác ghi dữ liệu quản trị phải là Admin.
        // Các vai trò nghiệp vụ phải dùng API /api/<vai-tro>/... tương ứng để service kiểm tra quyền sở hữu/phân công.
        if(isAdminWriteApi(uri,request.getMethod()) && !hasAny(session,"Quản trị"))
        return deny(request,response,auth.homeFor(session));
        // Mặc định khóa toàn bộ API dùng chung. Người dùng nghiệp vụ chỉ được gọi API
        // theo namespace vai trò đã kiểm tra ở trên; tránh đọc dữ liệu toàn hệ thống qua GET.
        if(uri.startsWith("/api/") && !isRoleScopedApi(uri) && !uri.startsWith("/api/auth/")
        && !isSafeSharedReadApi(uri,request.getMethod()) && !hasAny(session,"Quản trị"))
            return deny(request,response,auth.homeFor(session));
        return true;
    }
    private boolean isAdminWriteApi(String uri,String method) {
        if("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method) || "OPTIONS".equalsIgnoreCase(method)) return false;
        // Minh chứng chung cấp Trường/Khoa là nghiệp vụ dùng chung của Quản trị và Thư ký chương trình.
        // Loại endpoint này ra trước khi kiểm tra prefix /api/minh-chung để tránh chặn nhầm Thư ký.
        if(uri.equals("/api/minh-chung-chung") || uri.startsWith("/api/minh-chung-chung/")) return false;
        List<String> prefixes=List.of(
        "/api/ctdt",
        "/api/bo-tieu-chuan",
        "/api/tieu-chuan",
        "/api/tieu-chi",
        "/api/nguoi-dung",
        "/api/nhom-tieu-chi",
        "/api/phan-cong",
        "/api/minh-chung",
        "/api/bao-cao"
        );
        return prefixes.stream().anyMatch(uri::startsWith);
    }
    private boolean isSafeSharedReadApi(String uri,String method) {
        if(!"GET".equalsIgnoreCase(method) && !"HEAD".equalsIgnoreCase(method)) return false;
        return uri.equals("/api/tieu-chi") || uri.startsWith("/api/tieu-chi/")
        || uri.equals("/api/tieu-chuan") || uri.startsWith("/api/tieu-chuan/")
        || uri.equals("/api/bo-tieu-chuan") || uri.equals("/api/ctdt") || uri.startsWith("/api/ctdt/")
        || uri.equals("/api/ke-hoach") || uri.equals("/api/minh-chung-chung")
        || uri.equals("/api/danh-sach-nguoi-dung");
    }
    private boolean isRoleScopedApi(String uri) {
        return uri.startsWith("/api/hoi-dong/") || uri.startsWith("/api/truong-nhom/")
        || uri.startsWith("/api/thanh-vien/") || uri.startsWith("/api/thu-ky/")
        || uri.startsWith("/api/khao-thi/") || uri.startsWith("/api/nhan-su-bao-cao/");
    }
    private boolean hasAny(HttpSession session,String... roles) {
        for(String role:roles) if(auth.hasRole(session,role)) return true;
        return false;
    }
    private boolean deny(HttpServletRequest req,HttpServletResponse res,String home) throws Exception {
        if(req.getRequestURI().startsWith("/api/")) {
            res.setStatus(403);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write("{\"message\":\"Bạn không có quyền sử dụng chức năng này\"}");
        } else res.sendRedirect(home);
        return false;
    }
}
