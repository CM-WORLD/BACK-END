package com.cms.world.repository;

import com.cms.world.domain.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberDto, Long> {
}
