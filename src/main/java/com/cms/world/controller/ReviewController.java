package com.cms.world.controller;


import com.cms.world.domain.vo.PageVo;
import com.cms.world.domain.vo.ReviewVo;
import com.cms.world.service.ReviewService;
import com.cms.world.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    /* 사용자별 또는 전체 리뷰 조회 */
    @GetMapping("/list")
    public Map<String, Object> list(HttpServletRequest request, @ModelAttribute PageVo pageVo) {
        return CommonUtil.renderResultByMap(service.list(pageVo, request));
    }

    @PostMapping("/")
    public Map<String, Object> create (HttpServletRequest request, @ModelAttribute ReviewVo vo) {
        try {
            return CommonUtil.successResultMap(service.create(vo, request));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.failResultMap(e.getMessage());
        }
    }
}
