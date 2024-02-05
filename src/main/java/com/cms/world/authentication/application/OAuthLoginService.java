package com.cms.world.authentication.application;


import com.cms.world.authentication.domain.AuthTokens;
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

//    public AuthTokens login(OAuthLoginParams params) {
//        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
//        StringBuilder sb = new StringBuilder();
//        Long memberId = findOrCreateMember(oAuthInfoResponse);
//        return authTokensGenerator.generate(memberId);
//    }

    public void testLogin (OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);

        System.out.println("oAuthInfoResponse.getNickname() = " + oAuthInfoResponse.getNickname());
        System.out.println("oAuthInfoResponse.getOAuthProvider() = " + oAuthInfoResponse.getOAuthProvider());
        System.out.println("oAuthInfoResponse.getProfileImg() = " + oAuthInfoResponse.getProfileImg());
        System.out.println("oAuthInfoResponse.getId() = " + oAuthInfoResponse.getId());
    }



    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByUidAndLoginType(oAuthInfoResponse.getId(), oAuthInfoResponse.getOAuthProvider())
                .map(MemberDto::getId).orElseGet(() -> newMember(oAuthInfoResponse));
    }

    /* naver, kakao 신규 가입 처리 */
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        MemberDto member = new MemberDto();
        member.setUid(String.valueOf(oAuthInfoResponse.getId()));
        member.setNickName(oAuthInfoResponse.getNickname());

        if(StringUtil.isEmpty(oAuthInfoResponse.getProfileImg())) {
            member.setProfileImg("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
        } else {
            member.setProfileImg(oAuthInfoResponse.getProfileImg());
        }
        member.setLoginType(oAuthInfoResponse.getOAuthProvider());
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
        member.setNickName(profile.getName());
        if(StringUtil.isEmpty(profile.getProfileImageUrl())) {
            member.setProfileImg("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
        } else {
            member.setProfileImg(profile.getProfileImageUrl());
        }
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


}