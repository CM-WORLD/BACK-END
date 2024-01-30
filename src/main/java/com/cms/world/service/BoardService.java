package com.cms.world.service;


import com.cms.world.oauth.MemberRepository;
import com.cms.world.domain.dto.BbsImgDto;
import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.repository.BbsImgRepository;
import com.cms.world.repository.BoardRepository;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    private final BbsImgRepository bbsImgRepository;

    private final S3UploadService s3UploadService;

    private final MemberRepository memberRepository;


    @Transactional(rollbackFor= {Exception.class})
    public int insert(BoardVo vo) throws Exception {

       BoardDto dto = new BoardDto();
       dto.setTitle(vo.getTitle());
       dto.setContent(vo.getContent());
       dto.setBbsCode(vo.getBbsCode());

       if (memberRepository.findById(vo.getMemberId()).isPresent()) {
         dto.setMemberDto(memberRepository.findById(vo.getMemberId()).get());
       } else {
         throw new Exception("boardService.insert: member not found");
       }

       BoardDto createdDto = repository.save(dto);

        if (vo.getImgList() != null && !vo.getImgList().isEmpty()) {
            for (MultipartFile imgFile : vo.getImgList()) {
                if (!imgFile.isEmpty()) {
                    String uploadUrl = s3UploadService.saveFile(imgFile, "board");
                    BbsImgDto imgDto = new BbsImgDto();
                    imgDto.setBoardDto(createdDto);
                    imgDto.setImgUrl(uploadUrl);
                    bbsImgRepository.save(imgDto);
                }
            }
        }
           return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    public Page<BoardDto> list (String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.DESC, "regDate"));
        Page<BoardDto> pageList = repository.findAllByBbsCodeContaining(type, pageable);
        return pageList;
    }

    public Page<BoardDto> listByMemId (Long memberId, String bbsCode, int page, int size) throws  Exception{
        Pageable pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.DESC, "regDate"));
        Optional<MemberDto> member = memberRepository.findById(memberId);
        if(!member.isPresent()) {
            throw new Exception("listByMemId: member not found");
        } else {
            return repository.findBoardDtoByMemberDto(member.get(), pageable);
        }
    }

    public BoardDto detailById (String bbsCode, Long id) {
        return repository.findBoardDtoByBbsCodeAndId(bbsCode, id);
    }
}
