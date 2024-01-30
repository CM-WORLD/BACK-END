package com.cms.world.oauth;

import com.cms.world.domain.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberDto, Long> {
     Optional<MemberDto> findByEmail(String email);

     Optional<MemberDto> findById(Long id);

     Optional<MemberDto> findByRefreshToken(String refreshToken);
}
