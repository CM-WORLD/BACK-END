package com.cms.world.repository;

import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyDto, Long> {

//    List<ReplyDto> findByBoardDto(BoardDto boardDto);

    @Query(value = "WITH RECURSIVE CommentHierarchy AS (\n" +
            "    SELECT ID, PRNT_ID, CONTENT, NICK_NM, RGTR_DT, UPT_DT, BBS_ID\n" +
            "    FROM bbs_reply\n" +
            "    WHERE PRNT_ID IS NULL and BBS_ID = 1 \n" +
            "\n" +
            "    UNION ALL\n" +
            "\n" +
            "    SELECT c.ID, c.PRNT_ID, c.CONTENT, c.NICK_NM, c.RGTR_DT,c.UPT_DT, c.BBS_ID\n" +
            "    FROM CommentHierarchy ch, bbs_reply c\n" +
            "    WHERE c.PRNT_ID = ch.ID and c.BBS_ID = 1\n" +
            ")\n" +
            "SELECT * FROM CommentHierarchy;" , nativeQuery = true)
    List<ReplyDto> findRepliesByBbsId(BoardDto boardDto);
}
