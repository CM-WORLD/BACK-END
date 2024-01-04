package com.cms.world.controller;


import com.cms.world.domain.vo.CmsPayVo;
import com.cms.world.service.CmsPayService;
import com.cms.world.utils.GlobalStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class CmsPayController {

    private final CmsPayService service;

    public CmsPayController (CmsPayService service) {
        this.service = service;
    }

    @PostMapping("/form")
    public Map<String, Object> form (CmsPayVo vo) {
        Map<String, Object> map = new HashMap<>();
        try {
            service.form(vo);
            map.put("status" , GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
            return map;
        } catch (Exception e) {
            map.put("status" , GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
            return map;
        }
    }
}
