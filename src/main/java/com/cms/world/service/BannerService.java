package com.cms.world.service;


import com.cms.world.domain.dto.BannerDto;
import com.cms.world.domain.vo.BannerVo;
import com.cms.world.repository.BannerRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    private final BannerRepository repository;

    private final S3UploadService uploadService;

    public BannerService(BannerRepository repository, S3UploadService uploadService) {
        this.repository = repository;
        this.uploadService = uploadService;
    }

    public List<BannerDto> list() {
        return repository.findAll();
    }

    public int insert(BannerVo vo) {
        try {
            String imgUrl = uploadService.saveFile(vo.getImg(), "banner");

            BannerDto dto = new BannerDto();
            dto.setComment(vo.getComment());
            dto.setHrefUrl(vo.getHrefUrl());
            dto.setImgUrl(imgUrl);
            dto.setStartDate(vo.getStartDate());
            dto.setEndDate(vo.getEndDate());

            repository.save(dto);
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (Exception e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }
}
