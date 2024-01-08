package com.cms.world.auth;


import com.cms.world.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<MemberDto> getByRtk (String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken);
    }


}
