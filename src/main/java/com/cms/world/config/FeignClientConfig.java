package com.cms.world.config;

import com.cms.world.http.feign.FeignClientErrorDecoder;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "com.cms.world")
@NoArgsConstructor
// 여러 애플리케이션의 경우 basePackages= { 공통 패키지명 } 으로 설정
public class FeignClientConfig {

    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder();
    }
}