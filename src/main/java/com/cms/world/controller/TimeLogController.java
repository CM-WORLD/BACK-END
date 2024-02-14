package com.cms.world.controller;


import com.cms.world.stepper.domain.StepperDto;
import com.cms.world.stepper.service.StepperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
public class TimeLogController {

    private final StepperService service;
    public TimeLogController (StepperService service) {
        this.service = service;
    }

    /* 커미션 아이디별로 로그 조회 */
    @GetMapping("/{cmsId}")
    public Map<String, List<StepperDto>> list (@PathVariable String cmsId) {
        Map<String, List<StepperDto>> listMap = new HashMap<>();
//        listMap.put("list", service.listByCmsId(cmsId));
        return listMap;
    }
}
