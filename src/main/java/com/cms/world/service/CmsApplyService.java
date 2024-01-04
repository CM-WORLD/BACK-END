package com.cms.world.service;


import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsApplyImgDto;
import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.repository.CmsApplyImgRepository;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.utils.GlobalStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class CmsApplyService {

    private final CmsApplyRepository repository;
    private final CmsApplyImgRepository imgRepository;

    private final S3UploadService uploadService;

    private final TimeLogService timeLogService;

    public CmsApplyService(CmsApplyRepository repository,
                           CmsApplyImgRepository imgRepository,
                           S3UploadService uploadService,
                           TimeLogService timeLogService) {
        this.repository = repository;
        this.imgRepository = imgRepository;
        this.uploadService = uploadService;
        this.timeLogService = timeLogService;
    }

    /* 커미션 신청 */
    @Transactional
    public int insert(CmsApplyVo vo) throws IOException{
            CmsApplyDto dto = new CmsApplyDto();
            dto.setStatus(vo.getStatus());
            dto.setContent(vo.getContent());
            dto.setNickName(vo.getNickName());
            dto.setBankOwner(vo.getBankOwner());
            CmsApplyDto newDto = repository.save(dto);

            if (vo.getImgList().size() > 0) {
                for (MultipartFile img : vo.getImgList()) {
                    String awsUrl = uploadService.saveFile(img, "apply");
                    CmsApplyImgDto imgDto = new CmsApplyImgDto();
                    imgDto.setImgUrl(awsUrl);
                    imgDto.setApplyDto(newDto);
                    imgRepository.save(imgDto);
                }
            }
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    /* 커미션 전체 리스트 조회 (등록 최신순) */
    public List<CmsApplyDto> list () {
        List<CmsApplyDto> list = repository.findAll(Sort.by("regDate"));
        return list;
    }

    /* 커미션 상태 변경??? :: 가능?? */
    @Transactional
    public int updateStatus (String id, String status) {
        try {
            CmsApplyDto dto = repository.findById(id).get();
            dto.setStatus(status);
//            timeLogService.recordLog(dto);

            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (Exception e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }

    /* 조건당 커미션 신청 수 조회 */
    public long cntByStatus (String status) {
        return repository.countByStatus(status);
    }

}
