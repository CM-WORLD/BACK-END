package com.cms.world.service;


import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsApplyImgDto;
import com.cms.world.domain.vo.ApplySrchVo;
import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.repository.CmsApplyImgRepository;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    .cmsType(vo.getCmsType())
                    .content(vo.getContent())
                    .userName(vo.getUserName())
                    .bankOwner(vo.getBnkOwner())
                    .build();

            repository.save(dto);

            for (MultipartFile img : vo.getImgList()) {
                String awsUrl = uploadService.saveFile(img, "apply");
                CmsApplyImgDto imgDto = CmsApplyImgDto.builder().applyDto(dto).imgUrl(awsUrl).build();
                imgRepository.save(imgDto);
            }
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (IOException e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }

    public List<CmsApplyDto> list (ApplySrchVo vo) {
        List<CmsApplyDto> list = repository.findAll(Sort.by("regDate"));
        //TODO:: regDate 하드코딩이라 바꾸고 싶다.
        return list;
    }

}
