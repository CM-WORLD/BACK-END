package com.cms.world.aop;

import com.cms.world.auth.MemberService;
import com.cms.world.auth.jwt.AuthTokens;
import com.cms.world.auth.jwt.AuthTokensGenerator;
import com.cms.world.auth.jwt.JwtTokenProvider;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.utils.GlobalStatus;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthTokensGenerator authTokensGenerator;

    private final MemberService memberService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("==================jwt 인증==================");
//
//        String authHeader = request.getHeader("Authorization");
//        String rtkValue = request.getHeader("RefreshToken");
//
//        System.out.println("authHeader = " + authHeader);
//        System.out.println("rtkValue = " + rtkValue);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new Exception("인증 정보가 존재하지 않음");
//        }
//        String atkValue = authHeader.substring(7); // atk 추출
//
//        if (jwtTokenProvider.validateToken(atkValue)) {
//            Long memberId = authTokensGenerator.extractMemberId(atkValue);
//            request.setAttribute("memberId", String.valueOf(memberId));
//            return true;
//        }
//
//        // atk, rtk 전부 만료
//        if(!jwtTokenProvider.validateToken(rtkValue)) {
//           throw new Exception("리프레시 토큰 만료, 로그인 필요");
//        } else {
//            //atk만 만료됨. atk만 재발급.
//            Optional<MemberDto> dto = memberService.getByRtk(rtkValue);
//            if (!dto.isPresent()) {
//                throw new Exception("일치하는 사용자 정보가 존재하지 않음");
//            } else {
//                // dto가 있다면 dto.getMemberId() 가지고 generate AccessToken을 한다.
//                Long memberId = dto.get().getId();
//                String newAtk = authTokensGenerator.generateAtk(memberId);
//                request.setAttribute("newAtk", newAtk);
//            }
//
//        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("==================jwt 인증 후처리==================");
        if (request.getAttribute("newAtk") != null) {
            log.info("새로운 atk 발급");
            response.setHeader("newAtk", String.valueOf(request.getAttribute("atk")));
        }

        if (request.getAttribute("newRtk") != null) {
            log.info("새로운 rtk 발급");
            response.setHeader("newRtk", String.valueOf(request.getAttribute("rtk")));
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
