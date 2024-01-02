package com.cms.world.service;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.repository.ReplyRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private final ReplyRepository repository;

    @Autowired
    private BoardRepository boardRepository;

    public ReplyService (ReplyRepository repository) {
        this.repository = repository;
    }

    public int insert (ReplyVo vo) {
        try {
            ReplyDto dto = new ReplyDto();
            dto.setContent(vo.getContent());
            dto.setNickName(vo.getNickName());
            dto.setParentId(vo.getParentId());

            //게시글 가져오기
            BoardDto bbsDto = boardRepository.findById(vo.getBoardId()).get();
            dto.setBoardDto(bbsDto);

            repository.save(dto);
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();

        } catch (Exception e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }


}
