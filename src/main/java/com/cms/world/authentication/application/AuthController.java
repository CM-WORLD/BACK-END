package com.cms.world.authentication.application;

import com.cms.world.authentication.domain.AuthTokensGenerator;
import com.cms.world.authentication.infra.kakao.KakaoLoginParams;
import com.cms.world.authentication.infra.naver.NaverLoginParams;
import com.cms.world.authentication.infra.twitter.TwitterApiClient;
import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.authentication.member.application.MemberService;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.utils.*;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private final JwtValidator jwtValidator;

    private final AuthTokensGenerator authTokensGenerator;


    /* 로그인 여부 체크 */
    @GetMapping("/login/check")
    public Map<String, Object> isLogined (HttpServletRequest request) {
        return jwtValidator.validate(request);
    }

    /* 프론트로부터 카카오 인가 코드를 받아서 처리한다. */
    @PostMapping("/process/kakao")
    public Map<String, Object> kakaoLogin (@RequestBody KakaoLoginParams params) {
        try {
            return CommonUtil.successResultMap(oAuthLoginService.login(params));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

    /* 카카오 로그인 처리 */
    @GetMapping("/sign/out/kakao")
    public void kakaoLogout (HttpServletResponse response) throws IOException {
        response.sendRedirect("https://kapi.kakao.com/v1/user/unlink"); // url 변경할것
    }

    @PostMapping("/process/naver")
    public Map<String, Object> naverLogin (@RequestBody NaverLoginParams params) {
        try {
            return CommonUtil.successResultMap(oAuthLoginService.login(params));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

    /* 트위터 프로필 리턴 */
    @PostMapping("/process/twitter")
    public Map<String, Object> twitterOauthCallback (@RequestBody Map<String, Object> codeMap) {
        String oauthToken = String.valueOf(codeMap.get("oauthToken"));
        String oauthVerifier = String.valueOf(codeMap.get("oauthVerifier"));

        TwitterProfile profile =  twitterApiClient.getTwitterProfile(oauthToken, oauthVerifier);
        return CommonUtil.successResultMap(oAuthLoginService.login(profile));
    }

    private final TwitterApiClient twitterApiClient;

    /* 트위터 로그인 페이지로 이동 */
    @GetMapping("/sign/in/twitter")
    public void  twitterOauthLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(twitterApiClient.getTwitterLoginUrl() + "&force_login=true");
        // force_login=true : 접속 시 마다 로그인 요구
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

    /* provider별 로그아웃 처리 */
    @PostMapping("/sign/out")
    public Map<String, Object> signOut (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();

        Long memberId = authTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출
        Optional<MemberDto> member = memberRepository.findById(memberId);

        // 1. 로그아웃할 사용자 정보 없는 경우
        if (memberId == null || !member.isPresent()) {
            map.put("status", GlobalStatus.BAD_REQUEST.getStatus());
            map.put("message", "로그아웃할 사용자 정보 없음");
            return map;
        }

        MemberDto dto = member.get();
        String accessToken = dto.getAccessToken();

        String provider = dto.getLoginType();
        if (provider.equals(GlobalCode.OAUTH_NAVER.getCode())) { //네이버인 경우
            oAuthLoginService.removeNaverAccessToken(accessToken);
        }
        else if (provider.equals(GlobalCode.OAUTH_KAKAO.getCode())) { //카카오인 경우
            oAuthLoginService.removeKakaoAccessToken(accessToken);
        }


        // problem......
        //공통 accessToken, refreshToken 삭제하기
        dto.setAccessToken(null);
        dto.setRefreshToken(null);
        memberRepository.save(dto);

        return map;
    }

}