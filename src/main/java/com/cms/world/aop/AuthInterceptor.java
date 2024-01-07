package com.cms.world.aop;

import com.cms.world.auth.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
//@RequiredArgsConstructor // 안됨.
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("==================로그인============");

        String authHeader = request.getHeader("Authorization");
        String rtkValue = request.getHeader("refreshToken");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            //필수 정보가 없으므로 무효
            request.setAttribute("status", "500: auth unavailable");
            return false;
        }
        String atkValue = authHeader.substring(7); // atk 추출
        // atk, rtk 값 로그 확인 완료.

        // atk 멀쩡하면 걍 true하고 넘어가라 !
        if(jwtTokenProvider.validateToken(atkValue)) {
            request.setAttribute("status", "200: ok");
            return true;
        }

        // atk invalid + rtk invalid
           if(!jwtTokenProvider.validateToken(rtkValue)) {
               // 로그인 다시 해야함.
               request.setAttribute("status", "415: login required");
           } else {
               //rtk로 atk를 재발급 받아서 request에 다시 넣어줘야 하는 상황.
               // Optional<MemberDto> dto = memberRepository.findByRefreshToken(String refreshToken);

               // dto가 없다면 잘못된 로그인 정보임 => request.setAttribute("status", "501: rtk not found");

               // dto가 있다면 dto.getMemberId() 가지고 generate AccessToken을 한다.


               // refreshToken이 만료되었는데 atk가 멀쩡한거는 알바 아님(잘 돌아가거든 위에서)

           }

        request.setAttribute("test", "jinvicky"); // 다음 controller에게 rtk, atk 전달 방법

        //interceptor에서 바로 response를 넘길 수는 없는 건가>

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("-===================== END ===================");
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("[{}] afterCompletion... interceptor 실행");
//    }
}
