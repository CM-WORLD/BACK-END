package com.cms.world.controller;

import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.KakaoAuthService;
import com.cms.world.service.KakaoMsgService;
import com.cms.world.service.S3UploadService;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class TestController {


    @GetMapping("/api/test/token")
    public Map<String, Object> test (HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", 200);

            if(!StringUtil.isEmpty((String) request.getAttribute("newAtk"))) {
                map.put("newAtk", (String) request.getAttribute("newAtk"));
            }
            return map;
            }
        catch (Exception e) {
            map.put("status", 500);
            return map;
        }
    }

}
