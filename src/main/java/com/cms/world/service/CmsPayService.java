package com.cms.world.service;


import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsPayDto;
import com.cms.world.domain.vo.CmsPayVo;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.repository.CmsPayRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class CmsPayService {

    private final CmsPayRepository repository;

    @Autowired
    private CmsApplyRepository cmsApplyRepository;

    public CmsPayService (CmsPayRepository repository) {
        this.repository = repository;
    }

    public int form (CmsPayVo vo) throws Exception {
        Optional<CmsApplyDto> applyDto = cmsApplyRepository.findById(vo.getApplyId());

        if (!applyDto.isPresent()) throw new Exception("applyDto for payment not found");

        CmsPayDto dto = new CmsPayDto();
        dto.setApplyDto(applyDto.get());
        dto.setPayAmt(vo.getPayAmt());
        dto.setComment(vo.getComment());

        LocalDateTime localDateTime = LocalDateTime.ofInstant(vo.getEndDate().toInstant(), ZoneId.systemDefault());
        dto.setEndDate(localDateTime);

        if(repository.save(dto) == null) throw new Exception("payRepository.save returned null");
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }
}
