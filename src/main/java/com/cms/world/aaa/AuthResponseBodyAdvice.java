package com.cms.world.aaa;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@ControllerAdvice
public class AuthResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // returnType의 컨트롤러 클래스가 TestController인 경우에만 true 반환
        return returnType.getContainingClass() == TestController.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        MyResponseWrapper wrapper = new MyResponseWrapper(body);
        wrapper.setNewAccessToken("testAttribute");

        return wrapper;
    }
}
