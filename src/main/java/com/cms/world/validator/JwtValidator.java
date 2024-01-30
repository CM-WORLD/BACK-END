package com.cms.world.validator;


import com.cms.world.authentication.member.application.MemberService;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.authentication.infra.JwtTokenProvider;
import com.cms.world.authentication.domain.AuthTokensGenerator;
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

    private final AuthTokensGenerator authTokensGenerator;

    public JwtValidator(
            JwtTokenProvider jwtTokenProvider,
            MemberService memberService,
            AuthTokensGenerator authTokensGenerator) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.authTokensGenerator = authTokensGenerator;
    }


    /* jwt 인증여부 확인 */
    public Map<String, Object> validate(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        if (request.getHeader("type") != null && request.getHeader("type").equals("public")) {
            map.put("status", GlobalStatus.ATK_VALID.getStatus());
            map.put("message", GlobalStatus.ATK_VALID.getMsg());
            return map;
        }

        String authHeader = request.getHeader("Authorization");
        String rtkValue = request.getHeader("RefreshToken");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            map.put("status", GlobalStatus.LOGIN_REQUIRED.getStatus());
            map.put("message", GlobalStatus.LOGIN_REQUIRED.getMsg());
            return map;
        }
        String atkValue = authHeader.substring(7); // atk 추출

        if (!jwtTokenProvider.validateToken(atkValue)) {
            if (!jwtTokenProvider.validateToken(rtkValue)) {
                map.put("status", GlobalStatus.LOGIN_REQUIRED.getStatus());
                map.put("message", GlobalStatus.LOGIN_REQUIRED.getMsg());
                return map;
            }

            Optional<MemberDto> dto = memberService.getByRtk(rtkValue);
            if (!dto.isPresent()) {
                map.put("status", 505);
                map.put("message", "존재하지 않는 사용자");
                return map;
            } else {
                //atk 재발급
                MemberDto member = dto.get();
                Long memberId = member.getId();
                String newAtk = authTokensGenerator.generateAtk(memberId);

                map.put("status", GlobalStatus.ATK_REISSUED.getStatus());
                map.put("message", GlobalStatus.ATK_REISSUED.getMsg());
                map.put("newAtk", newAtk);
            }
        } else {
            map.put("status", GlobalStatus.ATK_VALID.getStatus());
            map.put("message", GlobalStatus.ATK_VALID.getMsg());
        }
        return map;
    }

    /* jwt 인증 체크 */
    public boolean isAuthValid (int status) {
        return status == GlobalStatus.ATK_VALID.getStatus() || status == GlobalStatus.ATK_REISSUED.getStatus();
    }

    /* access token 발급 */
    public Map<String, Object> checkAndAddRefreshToken(Map<String, Object> jwtMap, Map<String, Object> resultMap) {
        if (jwtMap.get("status").equals(GlobalStatus.ATK_REISSUED.getStatus())) {
            resultMap.put("newAtk", jwtMap.get("newAtk"));
        }
        return resultMap;
    }
}
