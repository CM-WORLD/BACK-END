package com.cms.world.aaa;

import com.cms.world.auth.MemberRepository;
import com.cms.world.domain.dto.MemberDto;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.KakaoAuthService;
import com.cms.world.service.KakaoMsgService;
import com.cms.world.service.S3UploadService;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
