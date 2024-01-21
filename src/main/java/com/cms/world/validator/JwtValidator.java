package com.cms.world.validator;


import com.cms.world.auth.MemberService;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.security.jwt.JwtTokenProvider;
import com.cms.world.security.jwt.JwtTokensGenerator;
import com.cms.world.utils.GlobalStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtValidator {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;

    private final JwtTokensGenerator jwtTokensGenerator;

    public JwtValidator (
            JwtTokenProvider jwtTokenProvider,
            MemberService memberService,
            JwtTokensGenerator jwtTokensGenerator) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.jwtTokensGenerator = jwtTokensGenerator;
    }



    /* jwt 인증여부 확인 */
    public Map<String, Object> validate(HttpServletRequest request, String token) {
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
                String newAtk = jwtTokensGenerator.generateAtk(memberId);

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

    /* accessToken 재발급 */
}
