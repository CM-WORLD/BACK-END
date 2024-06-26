package com.cms.world.authentication.member.application;


import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.authentication.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /* 리프레시 토큰별로 회원 조회 */
    public Optional<MemberDto> getByRtk (String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken);
    }

    /* 회원 저장 */
    public MemberDto save (MemberDto memberDto) {
        return memberRepository.save(memberDto);
    }


}
