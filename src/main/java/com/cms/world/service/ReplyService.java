package com.cms.world.service;


import com.cms.world.auth.MemberRepository;
import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.repository.ReplyRepository;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository repository;

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;


    @Transactional
    public int insert (ReplyVo vo) throws Exception {
//        ReplyDto dto = new ReplyDto();
//        dto.setContent(vo.getContent());
//        dto.setRegDate(DateUtil.currentDateTime());
//        BoardDto bbsDto = boardRepository.findById(vo.getBbsId()).get();
//        dto.setBoardDto(bbsDto);
//
//        // parentId로 replyDto를 가져와서 신규 dto의 parent로 넣는다.
//        if(vo.getParentId() != 0) {
//        ReplyDto parent = repository.findById(vo.getParentId()).get();
//        dto.setParent(parent);
//        }
//        repository.save(dto);
//
//        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        return 1;
    }

    public List<ReplyDto> listByBbsId (Long bbsId, Long memberId) throws Exception {
        Optional<BoardDto> bbsDto = boardRepository.findById(bbsId);
        if (!bbsDto.isPresent()) throw new Exception("ReplyService.listByBbsId() : 게시글이 존재하지 않습니다.");

        List<ReplyDto> list = repository.findByBoardDtoOrderByGroupIdAscSequenceIdAsc(bbsDto.get());
        for (ReplyDto dto : list) {
            MemberDto memberDto = memberRepository.findById(dto.getMemberId()).get();
            dto.setNickName(memberDto.getNickName());
            dto.setMyReply(memberId == memberDto.getId());
        }
        return repository.findByBoardDtoOrderByGroupIdAscSequenceIdAsc(bbsDto.get());
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
