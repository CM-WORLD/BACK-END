package com.cms.world.authentication.application;

import com.cms.world.authentication.TwitterApiInfo;
import com.cms.world.authentication.domain.AuthTokens;
import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.infra.kakao.KakaoLoginParams;
import com.cms.world.authentication.infra.twitter.TwitterApiClient;
import com.cms.world.authentication.infra.twitter.TwitterInfoResponse;
import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.authentication.member.application.MemberService;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    private final MemberRepository memberRepository;

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

            //
            KakaoLoginParams params = new KakaoLoginParams();

            String code = String.valueOf(codeMap.get("code"));
            params.setAuthorizationCode(code);

            Map<String, Object> resultMap = oAuthLoginService.getMemberAndTokens(params);

//            AuthTokens authTokens = (AuthTokens) resultMap.get("tokens");
//            Long memberId = (Long) resultMap.get("memberId");
//
//            MemberDto dto = memberRepository.findById(memberId).get();
//            dto.setRefreshToken(authTokens.getRefreshToken()); // 리프레시 토큰 저장
//            dto.setLastLoginTime(DateUtil.currentDateTime());
//            memberRepository.save(dto);
//
//            respMap.put("status", GlobalStatus.SUCCESS.getStatus());
//            respMap.put("message", GlobalStatus.SUCCESS.getMsg());
//            respMap.put("tokens", resultMap.get("tokens"));
//            respMap.put("nick", dto.getNickName());

            return respMap;
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }


    private final TwitterApiInfo twitterApiInfo;

    /* 트위터 프로필 리턴 */
    @PostMapping("/process/twitter")
    public Map<String, Object> twitterOauthCallback (@RequestBody Map<String, Object> codeMap) {
        String oauthToken = String.valueOf(codeMap.get("oauthToken"));
        String oauthVerifier = String.valueOf(codeMap.get("oauthVerifier"));

        TwitterProfile profile =  twitterApiClient.getTwitterProfile(oauthToken, oauthVerifier); // test ok
        return CommonUtil.successResultMap(profile);
    }

    private final TwitterApiClient twitterApiClient;

    /* 트위터 로그인 페이지로 이동 */
    @GetMapping("/sign/in/twitter")
    public void  twitterOauthLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(twitterApiClient.getTwitterLoginUrl());
    }

    /* 로그아웃 시 토큰 폐기 */
    @PostMapping("/invalidate/token")
    public Map<String, Object> invalidateTokens (HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (jwtValidator.validate(request).get("status").equals(GlobalStatus.LOGIN_REQUIRED.getStatus())) {
                map.put("status", GlobalStatus.BAD_REQUEST.getStatus());
                map.put("message", "로그아웃할 인가 정보 없음");
                return map;
            }

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
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

}