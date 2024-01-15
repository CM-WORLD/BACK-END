package com.cms.world.service;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.repository.ReplyRepository;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
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
        dto.setRegDate(DateUtil.currentDateTime());
        BoardDto bbsDto = boardRepository.findById(vo.getBbsId()).get();
        dto.setBoardDto(bbsDto);

        if(vo.getParentId() != 0) {
            dto.setParentId(vo.getParentId());
        }
        ReplyDto newReply = repository.save(dto);

        newReply.setDepthPath(StringUtil.isEmpty(vo.getParentPath())
                        ? String.valueOf(newReply.getId())
                        : vo.getParentPath() + "/" + newReply.getId());

        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    public List<ReplyDto> listByBbsId (Long bbsId) {
        return repository.findRepliesByBbsId(bbsId);
    }

    public int delete (Long id) {
        repository.deleteById(id);
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    @Transactional
    public int update (ReplyVo vo) {
        ReplyDto dto = repository.findById(vo.getId()).get();
        dto.setContent(vo.getContent());
        dto.setUptDate(DateUtil.currentDateTime()); //분시초까지
//        repository.save(dto);
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

}
