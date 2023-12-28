package com.cms.world.controller;


import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.service.ReplyService;
import com.cms.world.utils.CommonUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService service;

    public ReplyController(ReplyService service) {
        this.service = service;
    }

    /* 댓글 리스트 */
    @GetMapping("/list")
    public List<ReplyDto> listByBoardId (Long id) {
        return service.listByBoardId(id);
    }

    /* 댓글 작성 */
    @PostMapping("/form")
    public Map<String, Object> form2 (@RequestBody ReplyVo vo) {
        return CommonUtil.getResultMapTest2(service.insert(vo));
    }
}
