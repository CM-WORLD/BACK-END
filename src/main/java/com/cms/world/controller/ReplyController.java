package com.cms.world.controller;


import com.cms.world.service.ReplyService;
import com.cms.world.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService service;

    /* 게시글당 댓글 조회 */
    @GetMapping("/list/{bbsId}")
    public Map<String, Object> list (@PathVariable Long bbsId) {
        return CommonUtil.resultMap(service.listByBbsId(bbsId));
    }

}
