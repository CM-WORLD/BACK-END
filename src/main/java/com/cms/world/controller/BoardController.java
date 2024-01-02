package com.cms.world.controller;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.BoardService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bbs")
public class BoardController {

    private final BoardService service;
    public BoardController (BoardService service) {
        this.service = service;
    }

    /* 공통 게시판 추가 */
    @PostMapping("/form")
    public Map<String, Object> form (@RequestBody BoardVo vo) {
        return CommonUtil.resultMap(service.insert(vo));
    }

    /* 커미션 신청 공지 게시판 */
    @GetMapping("/aply/cms")
    public Page<BoardDto> aplyCmsList (@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return getListByBbsCode(GlobalCode.BBS_APLY.getCode(), page, size);
    }

    /* 문의하기 게시판 */
    @GetMapping("/inquiry")
    public Page<BoardDto> inquiryList (@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return getListByBbsCode(GlobalCode.BBS_INQUIRY.getCode(), page, size);
    }

    public Page<BoardDto> getListByBbsCode (String type, int page, int size) {
        return service.list(type, page, size);
    }
}


