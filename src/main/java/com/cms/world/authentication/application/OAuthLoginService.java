package com.cms.world.authentication.application;


import com.cms.world.authentication.domain.AuthTokens;
import com.cms.world.authentication.infra.naver.NaverFeignApi;
import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthLoginParams;
import com.cms.world.authentication.domain.oauth.RequestOAuthInfoService;
import com.cms.world.authentication.domain.AuthTokensGenerator;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    //인터페이스는 생성자 주입으로만 가능하다. @RequiredArgsConstructor 불가능
//    private final NaverFeignApi naverFeignApi;



    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        Optional<MemberDto> member = memberRepository.findByUidAndLoginType(oAuthInfoResponse.getId(), oAuthInfoResponse.getOAuthProvider());
        if (member.isPresent()) {
            MemberDto dto = member.get();
            dto.setAccessToken(oAuthInfoResponse.getAccessToken()); // 네이버의 경우 액세스 토큰 저장
            memberRepository.save(dto);
            return dto.getId();
        } else return newMember(oAuthInfoResponse);
    }

    /* naver, kakao 신규 가입 처리 */
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        MemberDto member = new MemberDto();
        member.setUid(String.valueOf(oAuthInfoResponse.getId()));
        member.setNickName(getNickName(oAuthInfoResponse.getNickname()));
        member.setProfileImg(getProfileImg(oAuthInfoResponse.getProfileImg()));
        member.setLoginType(oAuthInfoResponse.getOAuthProvider());
        if (StringUtil.isEmpty(oAuthInfoResponse.getAccessToken())){
            member.setAccessToken(oAuthInfoResponse.getAccessToken());
        }
        return memberRepository.save(member).getId();
    }

    /* 트위터 조회/가입 분기처리 */
    public long findOrCreateMember (TwitterProfile profile) {
        Optional<MemberDto> member = memberRepository.findByUidAndLoginType(String.valueOf(profile.getId()), GlobalCode.OAUTH_TWITTER.getCode());
        return member.map(MemberDto::getId).orElseGet(() -> newMember(profile));
    }

    /* 트위터 신규 회원 가입 */
    public long newMember (TwitterProfile profile) {
        MemberDto member = new MemberDto();
        member.setUid(String.valueOf(profile.getId()));
        member.setNickName(getNickName(profile.getName()));
        member.setProfileImg(getProfileImg(profile.getProfileImageUrl()));
        member.setLoginType(GlobalCode.OAUTH_TWITTER.getCode());
        return memberRepository.save(member).getId();
    }

    /* 공통 리프레시 토큰, 로그인 시각 처리 */
    @Transactional
    public Map<String, Object> handleTokenAndLoginTime (long memberId) {
        Map<String, Object> map = new HashMap<>();
        AuthTokens authTokens = authTokensGenerator.generate(memberId);

        MemberDto dto = memberRepository.findById(memberId).get();
        dto.setRefreshToken(authTokens.getRefreshToken()); // 리프레시 토큰 저장
        dto.setLastLoginTime(DateUtil.currentDateTime()); // 로그인 시각 저장
        memberRepository.save(dto);

        map.put("tokens", authTokens);
        map.put("nick", dto.getNickName());
        map.put("provider", dto.getLoginType());
        return map;
    }

    /* 로그인 (트위터) */
    public Map<String, Object> login (TwitterProfile profile) {
        Long id = findOrCreateMember(profile);
        return handleTokenAndLoginTime(id);
    }

    /* 카카오, 네이버 로그인 */
    public Map<String, Object> login (OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long id = findOrCreateMember(oAuthInfoResponse);
        return handleTokenAndLoginTime(id);
    }

    /* 프로필 이미지 기본값 설정 */
    private String getProfileImg(String profileImg) {
        return StringUtil.isEmpty(profileImg) ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" : profileImg;
    }

    /* 닉네임 기본값 설정 */
    private String getNickName(String nickName) {
        String defaultNickName = "익명_" + String.valueOf(UUID.randomUUID()).substring(0, 5);
        return StringUtil.isEmpty(nickName) ? defaultNickName: nickName;
    }

    /* naver */
    public String deleteAccessToken (Long memberId) {
        // 멤버 아이디로 토큰 조회
        Optional<MemberDto> dto = memberRepository.findById(memberId);
        if (dto.isPresent()) {
            String accessToken = dto.get().getAccessToken();
//            Map<String, Object> map = naverFeignApi.deleteToken(accessToken, GlobalCode.OAUTH_NAVER.getCode());


//            System.out.println("map.get() = " + map.get("access_token"));
//            System.out.println("map.get(\"message\") = " + map.get("result"));
        }

        return "";
    }


}