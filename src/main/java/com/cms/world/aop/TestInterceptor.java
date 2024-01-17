package com.cms.world.aop;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@CrossOrigin
public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response, Object handler) throws Exception {
            if (request.getHeader("type").equals("public")) {
//                request.setAttribute("newAtk", "thisispulibc");
                log.info("모두에게 퍼블릭");
                return true;
            } else {
                request.setAttribute("newAtk", "thisisprivate");
                return true;
            }
//return false;
//        String authHeader = request.getHeader("Authorization");
//        String rtkValue = request.getHeader("RefreshToken");
//
//
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> map = new HashMap<>();
////            map.put("status", 205);
////            map.put("newAtk", "this is new atk....");
//
//            //지금 여기가 재발급이라고 그냥 가정할게
//
////            String jsonString = objectMapper.writeValueAsString(map);
////            response.setStatus(HttpServletResponse.SC_OK); // 상태 코드 설정 (예: 400 Bad Request)
////            response.getWriter().write(jsonString); // 응답에 메시지 작성
//
//            // 지금 1번방법 사용중( 틀린 방법은 없음)
//            // 2번: response.setStatus를 200코드 주고 response.getWriter().write여기에 커스텀 코드값과 data값을 Json형식으로 프론트로 전달
//            return true;
//        }
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("-===================== post ===================");
        //
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("[{}] afterCompletion... interceptor 실행");
    }
}
