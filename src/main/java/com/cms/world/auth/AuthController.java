package com.cms.world.auth;

import com.cms.world.security.jwt.JwtTokens;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    private final  MemberRepository memberRepository;

    private final MemberService memberService;

    private final JwtValidator jwtValidator;

    /* 로그인 여부 체크 */
    @GetMapping("/login/check")
    public Map<String, Object> isLogined (HttpServletRequest request) {
        return jwtValidator.validate(request);
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

            JwtTokens jwtTokens = (JwtTokens) resultMap.get("tokens");
            Long memberId = (Long) resultMap.get("memberId");

            MemberDto dto = memberRepository.findById(memberId).get();
            dto.setRefreshToken(jwtTokens.getRefreshToken()); // 리프레시 토큰 저장
            dto.setLastLoginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            memberRepository.save(dto);

            respMap.put("status", GlobalStatus.SUCCESS.getStatus());
            respMap.put("message", GlobalStatus.SUCCESS.getMsg());
            respMap.put("tokens", resultMap.get("tokens"));
            respMap.put("nick", dto.getNickName());

            return respMap;
        } catch (Exception e) {
            respMap.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            respMap.put("message", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());

            return respMap;
        }
    }

    /* 로그아웃 시 토큰 폐기 */
    @PostMapping("/invalidate/token")
    public Map<String, Object> invalidateTkns (HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String refreshToken = request.getHeader("RefreshToken");
            Optional<MemberDto> dto = memberService.getByRtk(refreshToken);

            if (dto.isPresent()) {
                MemberDto memberDto = dto.get();
                memberDto.setRefreshToken(null);
                memberService.save(memberDto);
            }
            map.put("status", GlobalStatus.SUCCESS.getStatus());
            map.put("message", GlobalStatus.SUCCESS.getMsg());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("message", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
            return map;
    }

}