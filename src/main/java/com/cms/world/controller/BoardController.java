package com.cms.world.controller;


import com.cms.world.security.jwt.JwtTokensGenerator;
import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.BoardService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/bbs")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    private final JwtTokensGenerator jwtTokensGenerator;

    private final JwtValidator jwtValidator;

    /* 공통 게시판 게시글 추가, X @RequestBody */
    @PostMapping("/form")
    public Map<String, Object> form (HttpServletRequest request, @Validated(BoardVo.BoardVoCheckSequence.class) BoardVo vo, BindingResult bindingResult) {
        try {
            Map<String, Object> jwtMap = jwtValidator.validate(request);
            if(!jwtValidator.isAuthValid((int)(jwtMap.get("status")))) {
                return jwtMap;
            }

            if(bindingResult.hasErrors()) {
                return CommonUtil.failResultMap(GlobalStatus.BAD_REQUEST.getStatus(), bindingResult.getFieldError().getDefaultMessage());
            }

            Long memberId = jwtTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출
            vo.setMemberId(memberId);
            Map<String, Object> resultMap = CommonUtil.renderResultByMap(service.insert(vo));
            jwtValidator.checkAndAddRefreshToken(jwtMap, resultMap);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.resultMap(GlobalStatus.EXECUTE_FAILED.getStatus());
        }
    }

    /* 커미션 신청 공지 게시판 */
    @GetMapping("/aply/cms")
    public Map<String, Object> notice (@RequestParam(name="page", defaultValue = "0") Integer page,
                                       @RequestParam(name="size", defaultValue = "10") Integer size) {
        return CommonUtil.renderResultByMap(getListByBbsCode(GlobalCode.BBS_APLY.getCode(), page, size));
    }


    /* 문의 리스트 전체 (어드민 조회용) */
    @GetMapping("/inquiry")
    public Page<BoardDto> inquiryList (@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer size) {
        return getListByBbsCode(GlobalCode.BBS_INQUIRY.getCode(), page, size);
    }

    /*회원별로 문의 조회 */
    @GetMapping("/inquiry/member")
    public Map<String, Object> inquiryByMemberId (@RequestParam(name= "page", defaultValue = "0") Integer page,
                                             @RequestParam(name ="size", defaultValue = "10") Integer size,
                                             HttpServletRequest request) {

        Map<String, Object> jwtMap = jwtValidator.validate(request);
        if(!jwtValidator.isAuthValid((int)(jwtMap.get("status")))) {
            return jwtMap;
        }

        try {
            Long memberId = jwtTokensGenerator.extractMemberIdFromReq(request);
            return CommonUtil.successResultMapWithJwt(service.listByMemId(memberId, GlobalCode.BBS_INQUIRY.getCode(), page, size), jwtMap);
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

    /* 게시판 상세 조회 */
    @GetMapping("/{bbsCode}/{id}")
    public Map<String, Object> getById (@PathVariable("bbsCode") String bbsCode, @PathVariable("id") Long id) {
        return CommonUtil.renderResultByMap(service.detailById(bbsCode, id));
    }

    public Page<BoardDto> getListByBbsCode (String type, int page, int size) {
        return service.list(type, page, size);
    }
}


