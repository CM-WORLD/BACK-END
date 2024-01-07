package com.cms.world.auth;

import com.cms.world.auth.jwt.AuthTokens;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    public AuthController(OAuthLoginService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }

    /* 프론트로부터 카카오 인가 코드를 받아서 처리한다. */
    @PostMapping("/process/kakao")
    public Map<String, Object> kakaoLogin(@RequestBody Map<String, Object> codeMap) throws JsonProcessingException {
        KakaoLoginParams params = new KakaoLoginParams();

        String code = String.valueOf(codeMap.get("code"));
        params.setAuthorizationCode(code);

        AuthTokens jwtToken = oAuthLoginService.login(params);

        Map<String, Object> map = new HashMap<>();
        map.put("result", "success");
        map.put("tokens", jwtToken);
        return map;
    }
}