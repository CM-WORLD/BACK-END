package com.cms.world.aop;


import com.cms.world.auth.MemberService;
import com.cms.world.security.jwt.JwtTokensGenerator;
import com.cms.world.security.jwt.JwtTokenProvider;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@CrossOrigin
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;

    private final JwtTokensGenerator jwtTokensGenerator;


    private boolean isLoginRequired (String authorizationHeader, String refreshToken) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) return true;
        String accessToken = authorizationHeader.substring(7); // atk 추출
        if (!jwtTokenProvider.validateToken(accessToken) && !jwtTokenProvider.validateToken(refreshToken)) return true;
        return false;
    }

    private void sendObjMappingData (HttpServletResponse response, GlobalStatus status) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.getStatus());
        map.put("msg", status.getMsg());

        String jsonString = objectMapper.writeValueAsString(map);
        response.addHeader("Content-Type", "application/json; charset=UTF-8");

        response.getWriter().write(jsonString);

    }

    @Override
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response, Object handler) throws Exception {
        if(!StringUtil.isEmpty(request.getHeader("type")) && request.getHeader("type").equals("public") ) return true;

        String authHeader = request.getHeader("Authorization");
        String rtkValue = request.getHeader("RefreshToken");

        if (isLoginRequired(authHeader, rtkValue)) { //로그인 필요한 경우
            sendObjMappingData(response, GlobalStatus.LOGIN_REQUIRED);
            return false;
        }

        if(!jwtTokenProvider.validateToken(authHeader.substring(7))) {
            Optional<MemberDto> dto = memberService.getByRtk(rtkValue);

            if (!dto.isPresent()) {
                sendObjMappingData(response, GlobalStatus.NOT_FOUND_USER);
                return false;
            } else { // 액세스 토큰 재발급
                Long memberId = dto.get().getId();
                String newAccessToken = jwtTokensGenerator.generateAtk(memberId);
                request.setAttribute("newAtk", newAccessToken);
                return true;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
