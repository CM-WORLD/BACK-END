package com.cms.world.controller;


import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.BoardService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import jakarta.servlet.http.HttpServletRequest;
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

    /* 공통 게시판 게시글 추가, X @RequestBody */
    @PostMapping("/form")
    public Map<String, Object> form (BoardVo vo) {
        try {
            return CommonUtil.resultMap(service.insert(vo));
        } catch (Exception e) {
            return CommonUtil.resultMap(GlobalStatus.EXECUTE_FAILED.getStatus());
        }
    }

    /* 커미션 신청 공지 게시판 */
    @GetMapping("/aply/cms")
    public Page<BoardDto> aplyCmsList (@RequestParam(name="page", defaultValue = "0") Integer page,
                                       @RequestParam(name="size", defaultValue = "10") Integer size) {
        return getListByBbsCode(GlobalCode.BBS_APLY.getCode(), page, size);
    }

    /* 문의 리스트 전체 (어드민 조회용) */
    @GetMapping("/inquiry")
    public Page<BoardDto> inquiryList (@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer size) {
        return getListByBbsCode(GlobalCode.BBS_INQUIRY.getCode(), page, size);
    }

    /*회원별로 문의 조회 */
    @GetMapping("/auth/inquiry/by/member")
    public Page<BoardDto> inquiryByMemberId (@RequestParam(name= "page", defaultValue = "0") Integer page,
                                             @RequestParam(name ="size", defaultValue = "10") Integer size,
                                             HttpServletRequest request) {
        Long memberId = Long.valueOf((String) request.getAttribute("memberId"));
        return service.listByMemberId(memberId, GlobalCode.BBS_INQUIRY.getCode(), page, size);
    }

    /* 게시판 상세 조회 */
    @GetMapping("/{bbsCode}/{id}")
    public Map<String, Object> getById (@PathVariable String bbsCode, @PathVariable Long id) {
        return CommonUtil.resultMap(service.detailById(bbsCode, id));
    }

    public Page<BoardDto> getListByBbsCode (String type, int page, int size) {
        return service.list(type, page, size);
    }
}


