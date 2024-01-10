package com.cms.world.controller;


import com.cms.world.domain.dto.BannerDto;
import com.cms.world.domain.vo.BannerVo;
import com.cms.world.service.BannerService;
import com.cms.world.utils.CommonUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bnr")
public class BannerController {

    private final BannerService service;

    public BannerController (BannerService service){
        this.service = service;
    }

    @GetMapping("/list")
    public List<BannerDto> list () {
        return service.list();
    }

    @PostMapping("/form")
    public Map<String, Object> form (BannerVo vo) { //관리자가 postman으로 할 것이라서 @RequestBody 제외
        return CommonUtil.resultMap(service.insert(vo));
    }
}
