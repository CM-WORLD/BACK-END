package com.cms.world.controller;


import com.cms.world.domain.dto.CommissionDto;
import com.cms.world.domain.vo.CommissionVo;
import com.cms.world.service.CommissionService;
import com.cms.world.utils.GlobalStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cms")
public class CommissionController {

    @Autowired
    CommissionService service;

    @PostMapping("/form")
    public Map<Integer, String> submit(CommissionVo vo) throws Exception {
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

}
