package com.cms.world.service;

import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.repository.ReplyRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyRepository repository;

    public ReplyService(ReplyRepository repository) {
        this.repository = repository;
    }

    @Autowired
    BoardRepository boardRepository;

    public int insert(ReplyVo vo) {
        try {
            ReplyDto dto = new ReplyDto();
            dto.setContent(vo.getContent());
            dto.setWriter(vo.getWriter());
            dto.setParentId(vo.getParentId());
            BoardDto boardDto = boardRepository.findById(vo.getBoardId()).get();
            dto.setBoardDto(boardDto);

            repository.save(dto);
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }

    /* 게시글당 댓글 리스트 */
    public List<ReplyDto> listByBoardId(Long id) {
        BoardDto dto = boardRepository.findById(id).get();
        return repository.findReplyDtoByBoardDto(dto);
    }


}
