package com.cms.world.service;


import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsApplyImgDto;
import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.repository.CmsApplyImgRepository;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
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
    public int insert(CmsApplyVo vo) {
        try {
            CmsApplyDto dto = CmsApplyDto.builder()
                    .cmsType(vo.getCmsType())
                    .content(vo.getContent())
                    .userName(vo.getUserName())
                    .bankOwner(vo.getBnkOwner())
                    .refundAccNo(vo.getRefundAccNo())
                    .build();

            repository.save(dto);

            for (MultipartFile img : vo.getImgList()) {
                String awsUrl = uploadService.saveFile(img, "apply");
                CmsApplyImgDto imgDto = CmsApplyImgDto.builder().applyDto(dto).imgUrl(awsUrl).build();
                imgRepository.save(imgDto);
            }
            timeLogService.recordLog(dto);

            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (IOException e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }

    /* 커미션 전체 리스트 조회 (등록 최신순) */
    public List<CmsApplyDto> list () {
        List<CmsApplyDto> list = repository.findAll(Sort.by("regDate"));
        //TODO:: regDate 하드코딩이라 바꾸고 싶다.
        return list;
    }

    /* 커미션 상태 변경 */
    @Transactional
    public int updateStatus (String id, String status) {
        try {
            CmsApplyDto dto = repository.findById(id).get();
            dto.setStatus(status);
            timeLogService.recordLog(dto);

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
