package com.huit.kiemdinh.config;

import com.huit.kiemdinh.security.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private final String storageRoot;

    public WebConfig(AuthInterceptor authInterceptor,
                     @Value("${app.storage.root}") String storageRoot){
        this.authInterceptor = authInterceptor;
        this.storageRoot = storageRoot;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String location = Path.of(storageRoot).toAbsolutePath().normalize().toUri().toString();
        registry.addResourceHandler("/uploads/**").addResourceLocations(location);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**", "/hoi-dong/**", "/hoi-dong", "/truong-nhom/**", "/truong-nhom", "/thanh-vien/**", "/thanh-vien", "/thu-ky/**", "/thu-ky", "/khao-thi/**", "/khao-thi", "/nhan-su-bao-cao/**", "/nhan-su-bao-cao", "/api/**", "/uploads/**")
                .excludePathPatterns("/api/auth/login", "/login", "/error", "/css/**", "/js/**");
    }
}
