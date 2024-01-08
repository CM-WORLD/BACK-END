package com.cms.world.auth;

import com.cms.world.auth.jwt.AuthTokens;
import com.cms.world.auth.jwt.AuthTokensGenerator;
import com.cms.world.auth.jwt.JwtTokenProvider;
import com.cms.world.domain.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    private final RequestOAuthInfoService requestOAuthInfoService;

    private final  MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;

    private final AuthTokensGenerator authTokensGenerator;


    /* interceptor 외 인증 여부 확인 */
    @GetMapping("/validate/token")
    public Map<String, Object> validateTk (HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String authHeader = request.getHeader("Authorization");
        String rtkValue = request.getHeader("RefreshToken");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            map.put("status", 500);
            map.put("msg", "인가 정보 없음");
            return map;
        }
        String atkValue = authHeader.substring(7); // atk 추출

        if(!jwtTokenProvider.validateToken(atkValue)) {
            if(!jwtTokenProvider.validateToken(rtkValue)) {
                map.put("status", 415);
                map.put("msg", "로그인 필요");
                return map;
            }

            Optional<MemberDto> dto = memberService.getByRtk(rtkValue);
            if (!dto.isPresent()) {
                map.put("status", 500);
                map.put("msg", "존재하지 않는 사용자");
                return map;
            } else {
                //atk 재발급
                Long memberId = dto.get().getId();
                String newAtk = authTokensGenerator.generateAtk(memberId);
                map.put("status", 205);
                map.put("msg", "액세스 토큰 재발급");
                map.put("newAtk", newAtk);
            }
        } else {
            map.put("status", 200);
            map.put("msg", "유효한 atk");
        }
        return map;
    }

    /* 프론트로부터 카카오 인가 코드를 받아서 처리한다. */
    @PostMapping("/process/kakao")
    public Map<String, Object> kakaoLogin(@RequestBody Map<String, Object> codeMap) {
            Map<String, Object> respMap = new HashMap<>();
        try {
            KakaoLoginParams params = new KakaoLoginParams();

            String code = String.valueOf(codeMap.get("code"));
            params.setAuthorizationCode(code);

            Map<String, Object> resultMap = oAuthLoginService.getMemberAndTokens(params);

            AuthTokens jwtTokens = (AuthTokens) resultMap.get("tokens");
            Long memberId = (Long) resultMap.get("memberId");

            respMap.put("result", "success");
            respMap.put("tokens", resultMap.get("tokens"));

            MemberDto dto = memberRepository.findById(memberId).get();
            dto.setRefreshToken(jwtTokens.getRefreshToken()); // 리프레시 토큰 저장
            memberRepository.save(dto);

            respMap.put("nick", dto.getNickName());

            return respMap;
        } catch (Exception e) {
            return respMap;
        }
    }
}