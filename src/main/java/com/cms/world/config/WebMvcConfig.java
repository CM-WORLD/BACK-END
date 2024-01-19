package com.cms.world.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    private final AuthInterceptor authInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        List<String> URL_PATTERNS = Arrays.asList("/api/**");
//        List<String> URL_EXCLUDE_PATTERNS = Arrays.asList("/api/process/kakao","/invalidate/token", "/css/**", "/images/**", "/js/**");
//
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns(URL_PATTERNS)
//                .excludePathPatterns(URL_EXCLUDE_PATTERNS);
//    }
//    private final MyWebFilter myWebFilter;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
