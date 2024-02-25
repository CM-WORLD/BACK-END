package com.cms.world.common.upload;


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

    public String saveFile(MultipartFile multipartFile, String folderPath) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        String tempFileName = multipartFile.getOriginalFilename();
        if (tempFileName.length() > 110) {
            tempFileName = "..." + tempFileName.substring(tempFileName.length() - 110);
        }

        //동일파일명 삭제 방지
        String imgUrl = folderPath + "/" + System.currentTimeMillis() + "_" + tempFileName;

        amazonS3.putObject(bucket, imgUrl, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, imgUrl).toString();
    }

}
