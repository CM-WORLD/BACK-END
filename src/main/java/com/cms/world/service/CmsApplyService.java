package com.cms.world.service;


import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsApplyImgDto;
import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.repository.CmsApplyImgRepository;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CmsApplyService {

    private final CmsApplyRepository repository;
    private final CmsApplyImgRepository imgRepository;

    private final S3UploadService uploadService;

    public CmsApplyService(CmsApplyRepository repository, CmsApplyImgRepository imgRepository, S3UploadService uploadService) {
        this.repository = repository;
        this.imgRepository = imgRepository;
        this.uploadService = uploadService;
    }

    @Transactional
    public int insert(CmsApplyVo vo) {
        try {
            CmsApplyDto dto = CmsApplyDto.builder()
                    .cms_tp(vo.getCmsType())
                    .content(vo.getContent())
                    .user_name(vo.getUserName())
                    .bank_owner(vo.getBnkOwner())
                    .build();

            repository.save(dto);

            for (MultipartFile img : vo.getImgList()) {
                String awsUrl = uploadService.saveFile(img, "apply");
                CmsApplyImgDto imgDto = CmsApplyImgDto.builder().applyDto(dto).img_url(awsUrl).build();
                imgRepository.save(imgDto);
            }
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (IOException e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }
}
