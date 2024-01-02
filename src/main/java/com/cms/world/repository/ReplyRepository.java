package com.cms.world.repository;

import com.cms.world.domain.dto.ReplyDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyDto, Long> {
}
