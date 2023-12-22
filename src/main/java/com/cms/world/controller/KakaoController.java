package com.cms.world.controller;


import com.cms.world.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoAuthService authService;

    //카카오 토큰 저장.
    @GetMapping("/social/kakao")
    public void saveToken(String code) {
        authService.saveToken(code);
    }

}
