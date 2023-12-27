package com.cms.world.controller;


import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.BoardService;
import com.cms.world.utils.CommonUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bbs")
public class BoardController {

    private final BoardService service;
    public BoardController (BoardService service) {
        this.service = service;
    }


    @PostMapping("/form")
    public Map<String, Object> form (@RequestBody BoardVo vo) {
        Map<String, Object> map = CommonUtil.getResultMapTest2(service.insert(vo));
        return map;
    }


    //
}

