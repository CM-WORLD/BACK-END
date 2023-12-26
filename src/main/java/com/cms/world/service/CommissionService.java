package com.cms.world.service;


import com.cms.world.domain.dto.CommissionDto;
import com.cms.world.domain.vo.CommissionVo;
import com.cms.world.repository.CommissionRepository;
import com.cms.world.utils.BoolStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommissionService {

    private final CommissionRepository repository;

    public CommissionService(CommissionRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private S3UploadService s3UploadService;

    public void insert(CommissionVo vo) throws Exception {
        try {
            String imgUrl = s3UploadService.saveFile(vo.getProfileImg(), "cmsList");

            CommissionDto dto = new CommissionDto();
            dto.setName(vo.getName());
            dto.setContent(vo.getContent());
            dto.setProfileImg(imgUrl);

            repository.save(dto);
        } catch (Exception e) {
            throw new Exception("cms insert error");
        }
    }

    public List<CommissionDto> list(String delYn) {
        return repository.findByDelYnContaining(delYn);
    }
}
