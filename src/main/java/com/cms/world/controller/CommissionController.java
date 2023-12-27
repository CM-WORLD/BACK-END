package com.cms.world.controller;

import com.cms.world.domain.dto.CommissionDto;
import com.cms.world.domain.vo.CommissionVo;
import com.cms.world.service.CommissionService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cms")
public class CommissionController {

    private final CommissionService service;

    public CommissionController (CommissionService service) {
        this.service = service;
    }

    @PostMapping("/form")
    public Map<Integer, String> submit(@RequestBody CommissionVo vo) throws Exception {
        Map<Integer, String> map = new HashMap<>();
        try {
            service.insert(vo);
            map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
        } catch (Exception e) {
            map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

    @GetMapping("/list")
    public List<CommissionDto> list () {
        List<CommissionDto> list = service.list("N");
        return list;
    }

    @PutMapping("/close/all")
    public Map<Integer, String> closeAll() {
        Map<Integer, String> resultMap = CommonUtil.getResultMap(service.toggleAllStatus(GlobalCode.CMS_CLOSED.getCode()));
        return resultMap;
    }

    @PutMapping("/open/all")
    public Map<Integer, String> openAll() {
        Map<Integer, String> resultMap = CommonUtil.getResultMap(service.toggleAllStatus(GlobalCode.CMS_OPENED.getCode()));
        return resultMap;
    }

    @PutMapping("/toggle")
    public Map<Integer, String> toggleStatus (@RequestBody Map<String, Long> data) {
        Map<Integer, String> resultMap = CommonUtil.getResultMap(service.toggleStatus(data.get("id")));
        return resultMap;
    }

}
