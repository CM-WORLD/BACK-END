package com.cms.world.controller;


import com.cms.world.domain.vo.BannerVo;
import com.cms.world.service.BannerService;
import com.cms.world.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/bnr")
@Tag(name = "배너 API", description = "배너 API")
public class BannerController {

    private final BannerService service;

    public BannerController (BannerService service){
        this.service = service;
    }

    @GetMapping("/list")
    @Operation(summary = "배너 목록", description = "배너 목록을 조회한다")
    public Map<String, Object> list () {
        return CommonUtil.renderResultByMap(service.list());
    }

    @PostMapping("/form")
    @Operation(summary = "배너 등록", description = "배너를 등록한다")
    public Map<String, Object> form (BannerVo vo) { //관리자가 postman으로 할 것이라서 @RequestBody 제외
        return CommonUtil.resultMap(service.insert(vo));
    }
}
