package com.cms.world.auth;


import com.cms.world.auth.jwt.AuthTokens;
import com.cms.world.auth.jwt.AuthTokensGenerator;
import com.cms.world.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(MemberDto::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        MemberDto member  = new MemberDto();
        member.setNickName("user_" + UUID.randomUUID().toString().substring(0, 8));
        member.setEmail(oAuthInfoResponse.getEmail());
        return memberRepository.save(member).getId();
    }
}