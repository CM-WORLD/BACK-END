package com.cms.world.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        //동일한 파일명의 경우 중복되면 사라지므로 앞에 특별 uuid를 붙이는 것이 필요할 듯.

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }
}
