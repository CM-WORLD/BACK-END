package com.cms.world.authentication.application;


import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthLoginParams;
import com.cms.world.authentication.domain.oauth.RequestOAuthInfoService;
import com.cms.world.authentication.domain.AuthTokensGenerator;
import com.cms.world.authentication.member.domain.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

//    public JwtTokens login(OAuthLoginParams params) {
//        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
//        Long memberId = findOrCreateMember(oAuthInfoResponse);
//        return jwtTokensGenerator.generate(memberId);
//    }

    public Map<String, Object> getMemberAndTokens(OAuthLoginParams params) {
        Map<String, Object> map = new HashMap<>();
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);

        map.put("tokens", authTokensGenerator.generate(memberId));
        map.put("memberId", memberId);
        return map;
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        String email = "jinvicky@naver.com";
        return memberRepository.findByEmail(email)
                .map(MemberDto::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

//    /* uid와 provider로 사용자 중복구분하기 */
//    private Long selectOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
//        return memberRepository.findByUidAndLoginTp(oAuthInfoResponse.getUid(), oAuthInfoResponse.getOAuthProvider().toString())
//                .map(MemberDto::getId)
//                .orElseGet(() -> newMember(oAuthInfoResponse));
//    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        MemberDto member  = new MemberDto();
        member.setNickName("user_" + UUID.randomUUID().toString().substring(0, 8));
//        member.setEmail(oAuthInfoResponse.getEmail());

        // 현재 존재하는 member id의 최댓값을 가져온다.
        //
        return memberRepository.save(member).getId();
    }
}