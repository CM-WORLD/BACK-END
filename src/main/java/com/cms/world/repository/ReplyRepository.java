package com.cms.world.repository;

import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyDto, Long> {

    List<ReplyDto> findByBoardDto(BoardDto dto);

}
