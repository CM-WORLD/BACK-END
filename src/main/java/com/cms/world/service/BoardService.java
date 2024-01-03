package com.cms.world.service;


import com.cms.world.domain.dto.BbsImgDto;
import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.repository.BbsImgRepository;
import com.cms.world.repository.BoardRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class BoardService {

    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private BbsImgRepository bbsImgRepository;

    @Autowired
    private S3UploadService s3UploadService;



    @Transactional(rollbackFor= {Exception.class})
    public int insert(BoardVo vo) throws IOException {

           BoardDto dto = new BoardDto();
           dto.setTitle(vo.getTitle());
           dto.setContent(vo.getContent());
           dto.setBbsCode(vo.getBbsCode());
           dto.setNickName(vo.getNickName());

           BoardDto createdDto = repository.save(dto);

           for (MultipartFile imgFile : vo.getImgList()) {
               if(!imgFile.isEmpty()) {
                String uploadUrl = s3UploadService.saveFile(imgFile, "board");
                BbsImgDto imgDto = new BbsImgDto();
                imgDto.setBoardDto(createdDto);
                imgDto.setImgUrl(uploadUrl);
                bbsImgRepository.save(imgDto);
               }
           }
           return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    public Page<BoardDto> list (String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.DESC, "regDate"));
        Page<BoardDto> pageList = repository.findAllByBbsCodeContaining(type, pageable);
        return pageList;
    }

    public Page<BoardDto> listByNickName (String bbsCode, String nickName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.DESC, "regDate"));
        Page<BoardDto> pageList = repository.findAllByBbsCodeAndNickNameContainingIgnoreCase(bbsCode, nickName, pageable);
        return pageList;
    }
}
