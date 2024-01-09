package com.cms.world.controller;


import com.cms.world.service.ReviewService;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @GetMapping("/list")
    public Map<String, Object> list () {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("data", service.list());
            map.put("status", GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
        } catch (Exception e) {
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }
}
