package com.cms.world.config;


import com.cms.world.aop.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    public WebMvcConfig (AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> URL_PATTERNS = Arrays.asList("/apply/auth/**");
//        registry.addInterceptor(new AuthInterceptor()) // 이거 대신 위 주입 사용
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(URL_PATTERNS)
                .excludePathPatterns("/css/**", "/images/**", "/js/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
