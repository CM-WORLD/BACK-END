package com.cms.world.repository;

import com.cms.world.domain.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardDto, Long> {

    Page<BoardDto> findAllByBbsCodeContaining(String type,
                                        Pageable pageable);

    /* 게시판 코드 + 닉네임(대소문자 구분 X) */
    Page<BoardDto> findAllByBbsCodeAndNickNameContainingIgnoreCase(String bbsCode, String nickName, Pageable pageable);

    BoardDto findBoardDtoByBbsCodeAndId(String bbsCode, Long id);
}
