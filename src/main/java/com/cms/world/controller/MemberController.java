package com.cms.world.controller;


import com.cms.world.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    public MemberController (MemberService service) {
        this.service = service;
    }

    @PostMapping("/form")
    public Map<String, Object> form () {
        return new HashMap<>();
    }
}
