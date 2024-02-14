package com.cms.world.stepper.service;


import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.apply.repository.CmsApplyRepository;
import com.cms.world.apply.service.CmsApplyService;
import com.cms.world.stepper.domain.StepperDto;
import com.cms.world.stepper.repository.StepperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepperService {

    private final StepperRepository repository;

    private final CmsApplyRepository cmsApplyRepository;

    /* 신청별 작업기록 저장 */
    public void insertStep (String cmsApplyId, String status) throws Exception {
        Optional<CmsApplyDto> applyDto = cmsApplyRepository.findById(cmsApplyId);

        if(!applyDto.isPresent()) throw new Exception ("존재하지 않는 신청 아이디입니다.");
        StepperDto stepperDto = new StepperDto();
        stepperDto.setStatus(status);
        stepperDto.setApplyDto(applyDto.get());

        if(repository.save(stepperDto) == null) throw new Exception("신청별 작업기록 저장 실패");
    }
}
