package com.cms.world.service;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.repository.ReplyRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyRepository repository;

    @Autowired
    private BoardRepository boardRepository;

    public ReplyService (ReplyRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public int insert (ReplyVo vo) throws Exception {

        ReplyDto dto = new ReplyDto();
        dto.setContent(vo.getContent());

        if(vo.getParentId() == null) {
            dto.setDepthPath("0");
        } else {
            ReplyDto parentDto = repository.findById(vo.getParentId()).get();
            dto.setDepthPath(parentDto.getDepthPath() + "/" + parentDto.getId());
        }
        dto.setParentId(vo.getParentId());

        BoardDto bbsDto = boardRepository.findById(vo.getBbsId()).get();
        dto.setBoardDto(bbsDto);

        repository.save(dto);
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    public List<ReplyDto> listByBbsId (Long bbsId) {
        return repository.findRepliesByBbsId(bbsId);
    }

    public int delete (Long id) {
        repository.deleteById(id);
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

}
