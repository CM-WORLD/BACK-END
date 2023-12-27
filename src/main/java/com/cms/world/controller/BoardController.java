package com.cms.world.controller;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.BoardService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /* 커미션 신청 공지 게시판 */
    @GetMapping("/aply/cms")
    public List<BoardDto> aplyCmsList () {
        List<BoardDto> list = service.list(GlobalCode.BBS_APLY.getCode());
        return list;
    }


    //
}

