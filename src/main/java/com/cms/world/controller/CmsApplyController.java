package com.cms.world.controller;


import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.service.CmsApplyService;
import com.cms.world.utils.GlobalStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apply")
public class CmsApplyController {

    private CmsApplyService service;
    public CmsApplyController (CmsApplyService service){
        this.service = service;
    }

    @PostMapping("/form")
    public Map<Integer, String> submit (CmsApplyVo vo) {
        Map<Integer, String> map = new HashMap<>();
        if (service.insert(vo) == GlobalStatus.EXECUTE_SUCCESS.getStatus()) {
            map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
        } else {
            map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }
}
