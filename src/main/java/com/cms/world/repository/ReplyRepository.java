package com.cms.world.repository;

import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyDto, Long> {

    @Query(value = "WITH RECURSIVE CommentHierarchy AS (\n" +
            "    SELECT ID, PRNT_ID, CONTENT, DEPTH, RGTR_DT, UPT_DT, BBS_ID\n" +
            "    FROM bbs_reply\n" +
            "    WHERE BBS_ID = :bbsId AND PRNT_ID IS NULL\n" +
            "\n" +
            "    UNION ALL\n" +
            "\n" +
            "    SELECT c.ID, c.PRNT_ID, c.CONTENT, c.DEPTH, c.RGTR_DT, c.UPT_DT, c.BBS_ID\n" +
            "    FROM bbs_reply c\n" +
            "             INNER JOIN CommentHierarchy ch ON c.PRNT_ID = ch.ID\n" +
            "    WHERE c.BBS_ID = :bbsId\n" +
            ")\n" +
            "SELECT * FROM CommentHierarchy ORDER BY DEPTH", nativeQuery = true)
    List<ReplyDto> findRepliesByBbsId(@Param("bbsId") Long bbsId);
}
