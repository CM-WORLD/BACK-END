package com.cms.world.controller;


import com.cms.world.domain.vo.BoardVo;
import com.cms.world.domain.vo.PageVo;
import com.cms.world.domain.vo.ReviewVo;
import com.cms.world.service.ReviewService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    private final JwtValidator jwtValidator;

    /* 전체 리뷰 조회 */
    @GetMapping("/list")
    public Map<String, Object> list(HttpServletRequest request, @ModelAttribute PageVo pageVo) {
        return CommonUtil.successResultMap(service.list(request,pageVo, "N"));
    }

    /* 사용자별 리뷰 조회 */
    @GetMapping("/member/list")
    public Map<String, Object> listByMemberId (HttpServletRequest request, @ModelAttribute PageVo pageVo) {
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        if(!jwtValidator.isAuthValid((int)(jwtMap.get("status")))) {
            return jwtMap;
        }
        return CommonUtil.successResultMapWithJwt(service.list(request, pageVo, "Y"), jwtMap);
    }

    /* 리뷰  작성 */
    @PostMapping("/create")
    public Map<String, Object> create (HttpServletRequest request, @ModelAttribute @Validated(ReviewVo.ReviewVoCheckSequence.class) ReviewVo vo, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
                return CommonUtil.failResultMap(GlobalStatus.BAD_REQUEST.getStatus(), bindingResult.getFieldError().getDefaultMessage());
            }

            Map<String, Object> jwtMap = jwtValidator.validate(request);
            if(!jwtValidator.isAuthValid((int)(jwtMap.get("status")))) {
                return jwtMap;
            }
            return CommonUtil.successResultMapWithJwt(service.create(vo, request), jwtMap);
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

    /* 리뷰 비공개/공개 토글 처리 */
    @PutMapping("/toggle/{id}")
    public Map<String, Object> toggle (HttpServletRequest request, @PathVariable("id") Long id) {
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        System.out.println("id = " + id);
        try {
            if(!jwtValidator.isAuthValid((int)(jwtMap.get("status")))) {
                return jwtMap;
            }
            return CommonUtil.successResultMapWithJwt(service.toggle(id), jwtMap);
        } catch (Exception e) {
            return CommonUtil.failResultMapWithJwt(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage(), jwtMap);
        }
    }
}
