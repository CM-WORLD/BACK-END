package com.cms.world.service;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    public int insert(BoardVo vo) {
       BoardDto dto = BoardDto.builder()
               .title(vo.getTitle())
               .content(vo.getContent())
               .writer(vo.getWriter())
               .type(vo.getType())
               .build();

        repository.save(dto);
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    public List<BoardDto> list (String type) {
        return repository.findByTypeContaining(type);
    }
}
