package com.cms.world.controller;


import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.service.ReplyService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    @Autowired
    private ReplyService service;

    /* 게시글당 댓글 조회 */
    @GetMapping("/{bbsId}")
    public Map<String, Object> list (@PathVariable("bbsId") Long bbsId) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("data", service.listByBbsId(bbsId));
            map.put("status", GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
            return map;
        } catch(Exception e) {
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
            return CommonUtil.renderResultByMap(e.getMessage());
        }
    }

    @PostMapping("/")
    public Map<String,Object> insert (ReplyVo vo) throws Exception {
        System.out.println("vo.toString() = " + vo.toString());
        return CommonUtil.resultMap(service.insert(vo));
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> delete (@PathVariable("id") Long id) throws Exception {
        System.out.println("id = " + id);
        return CommonUtil.resultMap(service.delete(id));
    }

    @PutMapping("/")
    public Map<String, Object> update (@RequestBody ReplyVo vo) {
        return CommonUtil.resultMap(service.update(vo));
    }
}
