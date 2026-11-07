package com.huit.kiemdinh.config;
import com.huit.kiemdinh.security.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
        .addPathPatterns("/admin/**", "/hoi-dong/**", "/hoi-dong", "/truong-nhom/**", "/truong-nhom", "/thanh-vien/**", "/thanh-vien", "/thu-ky/**", "/thu-ky", "/khao-thi/**", "/khao-thi", "/nhan-su-bao-cao/**", "/nhan-su-bao-cao", "/api/**", "/uploads/**")
        .excludePathPatterns("/api/auth/login", "/login", "/error", "/css/**", "/js/**");
    }
}
