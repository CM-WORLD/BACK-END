package com.cms.world.oauth.controller;

import com.cms.world.oauth.KakaoLoginParams;
import com.cms.world.oauth.MemberRepository;
import com.cms.world.oauth.MemberService;
import com.cms.world.oauth.OAuthLoginService;
import com.cms.world.oauth.domain.TwitterApiInfo;
import com.cms.world.security.jwt.JwtTokens;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OauthController {

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


    private final TwitterApiInfo twitterApiInfo;

    // 콜백 url
    @PostMapping("/process/twitter")
    public Map<String, Object> twitterOauthCallback (@RequestBody Map<String, Object> codeMap) {

        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterApiInfo.getApiKey(),twitterApiInfo.getApiSecret());
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

        String oauthToken = String.valueOf(codeMap.get("oauthToken"));
        String oauthVerifier = String.valueOf(codeMap.get("oauthVerifier"));

        OAuthToken accessToken = oauthOperations.exchangeForAccessToken(
                new AuthorizedRequestToken(new OAuthToken(oauthToken, ""), oauthVerifier), null);

        System.out.println("Token Value:- accesstoken");
        Twitter twitter = new TwitterTemplate(twitterApiInfo.getApiKey(),
                twitterApiInfo.getApiSecret(),
                accessToken.getValue(),
                accessToken.getSecret());
        TwitterProfile profile = twitter.userOperations().getUserProfile();

        profile.getId(); // 고유 사용자 uid 느낌

        return CommonUtil.successResultMap(profile);
    }

    /* 트위터 로그인, 호출시 트위터 인증 페이지로 이동 */
    @GetMapping("/sign/in/twitter")
    public void  twitterOauthLogin(HttpServletResponse response) throws IOException {
        TwitterConnectionFactory connectionFactory =
                new TwitterConnectionFactory(twitterApiInfo.getApiKey(),twitterApiInfo.getApiSecret());
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

        OAuthToken requestToken = oauthOperations.fetchRequestToken(twitterApiInfo.getCallbackUrl(), null);
        String token = requestToken.getValue();
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(token, OAuth1Parameters.NONE);

        response.sendRedirect(authorizeUrl);
    }


    /* 로그아웃 시 토큰 폐기 */
    @PostMapping("/invalidate/token")
    public Map<String, Object> invalidateTkns (HttpServletRequest request) {
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
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("message", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
            return map;
    }

}