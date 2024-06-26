package com.cms.world.authentication.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberDto, Long> {

     Optional<MemberDto> findById(Long id);

     Optional<MemberDto> findByRefreshToken(String refreshToken);


     // 사용자를 uid + loginType으로 찾는다.
     Optional<MemberDto> findByUidAndLoginType(String uid, String loginTp);

}
