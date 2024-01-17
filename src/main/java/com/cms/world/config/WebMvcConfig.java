package com.cms.world.config;


import com.cms.world.aop.AuthInterceptor;
import com.cms.world.aop.TestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TestInterceptor testInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> URL_PATTERNS = Arrays.asList("/api/**");
        registry.addInterceptor(testInterceptor)
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
