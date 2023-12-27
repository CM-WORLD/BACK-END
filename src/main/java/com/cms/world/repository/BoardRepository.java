package com.cms.world.repository;

import com.cms.world.domain.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardDto, Long> {
}
