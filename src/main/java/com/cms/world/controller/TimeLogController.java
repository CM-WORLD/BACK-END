package com.cms.world.controller;


import com.cms.world.domain.dto.TimeLogDto;
import com.cms.world.service.TimeLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/log")
public class TimeLogController {

    private final TimeLogService service;
    public TimeLogController (TimeLogService service) {
        this.service = service;
    }

    /* 커미션 아이디별로 로그 조회 */
    @GetMapping("/{cmsId}")
    public Map<String, List<TimeLogDto>> list (@PathVariable String cmsId) {
        Map<String, List<TimeLogDto>> listMap = new HashMap<>();
        listMap.put("list", service.listByCmsId(cmsId));
        return listMap;
    }
}
