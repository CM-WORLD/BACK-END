package com.cms.world.controller;

import com.cms.world.service.KakaoAuthService;
import com.cms.world.service.KakaoMsgService;
import com.cms.world.service.S3UploadService;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final S3UploadService s3UploadService;

    private final KakaoAuthService kakaoAuthService;

    private final KakaoMsgService kakaoMsgService;

    @PostMapping("/file")
    public String test(MultipartFile multipartFile) throws IOException {
        return s3UploadService.saveFile(multipartFile, "/apply"); //신청서
    }


//    public Map<Integer, String> kakaoTest(String code) {
//        Map<Integer, String> map = new HashMap<>();
//        if (kakaoAuthService.saveToken(code) == GlobalStatus.EXECUTE_SUCCESS.getStatus()) {
//            map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
//        } else map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
//        return map;
//    }

}
