package com.cms.world.aop;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader("type").equals("public")) {
            log.info("모두에게 퍼블릭");
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        String rtkValue = request.getHeader("RefreshToken");

        log.info("authHeader: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);

            String jsonString = objectMapper.writeValueAsString(map);
            response.setStatus(HttpServletResponse.SC_OK); // 상태 코드 설정 (예: 400 Bad Request)
            response.getWriter().write(jsonString); // 응답에 메시지 작성

            // 지금 1번방법 사용중( 틀린 방법은 없음)
            // 2번: response.setStatus를 200코드 주고 response.getWriter().write여기에 커스텀 코드값과 data값을 Json형식으로 프론트로 전달
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("-===================== END ===================");
        //
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("[{}] afterCompletion... interceptor 실행");
    }
}
