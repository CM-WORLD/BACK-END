package com.cms.world.service;


import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.domain.dto.BoardDto;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.repository.BoardRepository;
import com.cms.world.repository.ReplyRepository;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository repository;

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public int insert (ReplyVo vo) throws Exception {
        ReplyDto dto = new ReplyDto();
        dto.setContent(vo.getContent()); //내용
        BoardDto bbsDto = boardRepository.findById(vo.getBbsId()).get();
        dto.setBoardDto(bbsDto); //게시글 아이디
        dto.setRegDate(DateUtil.currentDateTime()); //등록일
        dto.setMemberId(vo.getMemberId()); // memberId 저장

        if (vo.getParentReplyId() == null ) {
            ReplyDto newReply = repository.save(dto);
            newReply.setGroupId(newReply.getId()); // save 후에 본인 아이디로 groupId 채우기

        } else {
            // 그룹별로 시퀀스를 정하고, 부모 레벨 + 1로 레벨을 정한다.
            ReplyDto parent = repository.findById(vo.getParentReplyId()).get();

            dto.setSequenceId(repository.getMaxSequenceId(parent.getGroupId()) + 1L); // 그룹아이디랑 pk랑 동일
            dto.setLevelId(parent.getLevelId() + 1);
            dto.setGroupId(parent.getGroupId());
            dto.setParentId(vo.getParentReplyId() + 1);
            repository.save(dto);
        }
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    public List<ReplyDto> listByBbsId (Long bbsId, Long memberId) throws Exception {
        Optional<BoardDto> bbsDto = boardRepository.findById(bbsId);
        if (!bbsDto.isPresent()) throw new Exception("ReplyService.listByBbsId() : 게시글이 존재하지 않습니다.");

        List<ReplyDto> list = repository.findByBoardDtoOrderByGroupIdAscSequenceIdAsc(bbsDto.get());
        for (ReplyDto dto : list) {
            MemberDto memberDto = memberRepository.findById(dto.getMemberId()).get();
            dto.setNickName(memberDto.getNickName());
            dto.setMyReply(memberId == memberDto.getId());

            int maxLevelId = repository.getMaxLevelId(dto.getGroupId());
            dto.setHasChildren(maxLevelId > dto.getLevelId());
            // 그룹 내 최대 레벨보다 현재 레벨이 더 작으면 자식이 있다는 뜻
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
        dto.setUptDate(DateUtil.currentDateTime());
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

}
