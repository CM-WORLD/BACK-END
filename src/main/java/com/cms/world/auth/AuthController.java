package com.cms.world.auth;

import com.cms.world.auth.jwt.AuthTokens;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    public AuthController(OAuthLoginService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }

    @GetMapping("/social/kakao")
    public String loginKakaoGet(String code, HttpServletResponse response) {
        KakaoLoginParams params = new KakaoLoginParams();
        params.setAuthorizationCode(code);

        AuthTokens jwtToken = oAuthLoginService.login(params);


        /* 로그인은 성공했으나 이후 redirect에 관한 프론트와의 처리가 안됨.... TODO */
        Cookie cookie = new Cookie("cmsUser", jwtToken.getAccessToken());
        System.out.println("jwtToken = " + jwtToken.getAccessToken());
        cookie.setMaxAge(3600); // 쿠키의 유효 시간 (초 단위)
        response.addCookie(cookie);

        return "redirect:";
    }
}