package com.cms.world.service;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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


    public Page<BoardDto> list (String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardDto> pageList = repository.findByTypeContaining(type, pageable);
        return pageList;
    }
}
