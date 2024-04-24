package com.cms.world.board.repository;

import com.cms.world.board.domain.BoardDto;
import com.cms.world.authentication.member.domain.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardDto, Long> {

    Page<BoardDto> findAllByBbsCodeContaining(String type,
                                        Pageable pageable);

    Page<BoardDto> findBoardDtoByMemberDto (MemberDto memberDto, Pageable pageable);

    /* 게시판 상세 */
    BoardDto findBoardDtoByBbsCodeAndId(String bbsCode, Long id);
}
