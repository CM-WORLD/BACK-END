package com.cms.world.auth;

import com.cms.world.auth.jwt.AuthTokens;
import com.cms.world.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    private final RequestOAuthInfoService requestOAuthInfoService;

    private final  MemberRepository memberRepository;


    /* 프론트로부터 카카오 인가 코드를 받아서 처리한다. */
    @PostMapping("/process/kakao")
    public Map<String, Object> kakaoLogin(@RequestBody Map<String, Object> codeMap) {
        KakaoLoginParams params = new KakaoLoginParams();

        String code = String.valueOf(codeMap.get("code"));
        params.setAuthorizationCode(code);

        AuthTokens jwtToken = oAuthLoginService.login(params);

        Map<String, Object> map = new HashMap<>();
        map.put("result", "success");
        map.put("tokens", jwtToken);

        //닉네임, 리프레시 토큰 저장
        OAuthInfoResponse response = requestOAuthInfoService.request(params);
        MemberDto dto = memberRepository.findByEmail(response.getEmail()).get();
        dto.setRefreshToken(jwtToken.getRefreshToken()); // 리프레시 토큰 저장
        memberRepository.save(dto);

        map.put("nick", dto.getNickName());

        return map;
    }
}