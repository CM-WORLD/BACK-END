package com.cms.world.oauth;


import com.cms.world.security.jwt.JwtTokens;
import com.cms.world.security.jwt.JwtTokensGenerator;
import com.cms.world.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final JwtTokensGenerator jwtTokensGenerator;
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

        map.put("tokens", jwtTokensGenerator.generate(memberId));
        map.put("memberId", memberId);
        return map;
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(MemberDto::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        MemberDto member  = new MemberDto();
        member.setNickName("user_" + UUID.randomUUID().toString().substring(0, 8));
//        member.setEmail(oAuthInfoResponse.getEmail());
        return memberRepository.save(member).getId();
    }
}