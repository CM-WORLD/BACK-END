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
        return CommonUtil.getResultMapTest2(service.insert(vo));
    }

    /* 커미션 신청 공지 게시판 */
    @GetMapping("/aply/cms")
    public List<BoardDto> aplyCmsList () {
        return service.list(GlobalCode.BBS_APLY.getCode());
    }

    /* 문의하기 게시판 */
    @GetMapping("/inquiry")
    public List<BoardDto> inquiryList () {
        return service.list(GlobalCode.BBS_INQUIRY.getCode());
    }
}

