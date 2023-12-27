package com.cms.world.repository;

import com.cms.world.domain.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardDto, Long> {

    List<BoardDto> findByTypeContaining(String type);
}
