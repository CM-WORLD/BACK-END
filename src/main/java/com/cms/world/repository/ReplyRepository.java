package com.cms.world.repository;

import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyDto, Long> {

    List<ReplyDto> findByBoardDtoOrderByGroupIdAscSequenceIdAsc(BoardDto boardDto);

    @Query(value = "SELECT MAX(SEQ_ID) FROM bbs_reply WHERE GRP_ID = :groupId", nativeQuery = true)
    int getMaxSequenceId(@Param("groupId") Long groupId);

    @Query(value = "SELECT MAX(LVL_ID) FROM bbs_reply WHERE GRP_ID = :groupId", nativeQuery = true)
    int getMaxLevelId (@Param("groupId") Long groupId);
}
