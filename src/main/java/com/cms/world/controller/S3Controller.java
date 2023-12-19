package com.cms.world.controller;


import com.cms.world.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3UploadService s3UploadService;

    @PostMapping("/file")
    public String test (MultipartFile multipartFile) throws IOException {
        // 1. db에 s3에 업로드한 파일 경로를 저장한다.
        // 2.
        //
        return s3UploadService.saveFile(multipartFile);
    }
}
