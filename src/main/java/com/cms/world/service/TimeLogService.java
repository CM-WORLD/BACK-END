package com.cms.world.service;


import com.cms.world.domain.dto.TimeLogDto;
import com.cms.world.repository.TimeLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeLogService {

    private final TimeLogRepository repository;

    public TimeLogService (TimeLogRepository repository) {
        this.repository = repository;
    }

    public List<TimeLogDto> listByCmsId(String cmsId) {
        return repository.findAll();
    }

//    public void recordLog (CmsApplyDto dto) {
//        TimeLogDto timeDto= TimeLogDto.builder().status(dto.getStatus()).applyDto(dto).build();
//        repository.save(timeDto);
//    }
}
