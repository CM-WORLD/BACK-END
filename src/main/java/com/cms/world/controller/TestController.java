package com.cms.world.controller;

import com.cms.world.auth.MemberRepository;
import com.cms.world.domain.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@Slf4j
public class TestController {

    private final MemberRepository memberRepository;

    public Mono<Map<String, Object>> fetchData () {
        return Mono.fromCallable(() -> {
            MemberDto member = memberRepository.findById(1L).orElse(null);
            if (member != null) {
                Map<String, Object> result = new HashMap<>();
                result.put("nickName", member.getNickName());
                return result;
            }
            return Collections.emptyMap();
        });
    }

    @GetMapping("/api/test/token")
    public Mono<Map<String, Object>> test (HttpServletRequest request, HttpServletResponse response) {
            return Mono.fromCallable(() -> fetchData().block());
    }

}
