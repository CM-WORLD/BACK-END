package com.cms.world.auth;

import com.cms.world.auth.jwt.AuthTokens;
import com.cms.world.auth.jwt.AuthTokensGenerator;
import com.cms.world.auth.jwt.JwtTokenProvider;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.utils.GlobalStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

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
                map.put("status", 505);
                map.put("msg", "존재하지 않는 사용자");
                return map;
            } else {
                //atk 재발급
                MemberDto member = dto.get();
                Long memberId = member.getId();
                String newAtk = authTokensGenerator.generateAtk(memberId);

                map.put("status", GlobalStatus.ATK_REISSUED.getStatus());
                map.put("msg", GlobalStatus.ATK_REISSUED.getMsg());
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

            MemberDto dto = memberRepository.findById(memberId).get();
            dto.setRefreshToken(jwtTokens.getRefreshToken()); // 리프레시 토큰 저장
            dto.setLastLoginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            memberRepository.save(dto);

            respMap.put("status", GlobalStatus.SUCCESS.getStatus());
            respMap.put("msg", GlobalStatus.SUCCESS.getMsg());
            respMap.put("tokens", resultMap.get("tokens"));
            respMap.put("nick", dto.getNickName());

            return respMap;
        } catch (Exception e) {
            respMap.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            respMap.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());

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
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
            return map;
    }

}