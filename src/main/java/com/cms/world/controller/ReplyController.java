package com.cms.world.controller;


import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.service.ReplyService;
import com.cms.world.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    @Autowired
    private ReplyService service;

    /* 게시글당 댓글 조회 */
    @GetMapping("/list/{bbsId}")
    public Map<String, Object> list (@PathVariable("bbsId") Long bbsId) {
        return CommonUtil.resultMap(service.listByBbsId(bbsId));
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
