package com.cms.world.authentication.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberDto, Long> {
     Optional<MemberDto> findByEmail(String email);

     Optional<MemberDto> findById(Long id);

     Optional<MemberDto> findByRefreshToken(String refreshToken);


     // 사용자를 id + uid 조합으로 찾기
     Optional<MemberDto> findByUidAndLoginType(Long uid, String loginTp);
}
