package com.cms.world.controller;

import com.cms.world.domain.vo.BoardVo;
import com.cms.world.service.KakaoAuthService;
import com.cms.world.service.KakaoMsgService;
import com.cms.world.service.S3UploadService;
import com.cms.world.utils.GlobalStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class TestController {


    @PostMapping("/test")
    public Map<String, Object> form200 (BoardVo vo) {
        System.out.println("title = " + vo.getTitle());
        System.out.println("content = " + vo.getContent());
        System.out.println("vo.getImgList().size() = " + vo.getImgList().size());
//        System.out.println("vo.toString()2 = " + imgList.size());
        return new HashMap<>();
//        return CommonUtil.resultMap(service.insert(vo));
    }
    
    @PostMapping("/apply/test")
    public void test (HttpServletRequest request) {
        System.out.println("request.getAttribute(\"test\") = " + request.getAttribute("test"));
        
    }

}
