package com.cms.world.service;


import com.cms.world.domain.dto.MemberDto;
import com.cms.world.repository.MemberRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public int insert(String email) {
        try {
            MemberDto dto = new MemberDto();
            dto.setNickName("user_" + UUID.randomUUID().toString().substring(0, 8));
            dto.setEmail(email);

            memberRepository.save(dto);
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (Exception e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }
}
