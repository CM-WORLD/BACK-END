package com.cms.world.service;


import com.cms.world.domain.dto.PostDto;
import com.cms.world.domain.vo.PostVo;
import com.cms.world.repository.PostRepository;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PostService {

    private PostRepository repository;

    private S3UploadService s3UploadService;

    public PostService (PostRepository postRepository, S3UploadService s3UploadService) {
        this.repository = postRepository;
        this.s3UploadService = s3UploadService;
    }

    public void add(PostVo vo, String folderPath) throws IOException {
        String imgUrl = s3UploadService.saveFile(vo.getImg(), folderPath);
        PostDto dto = PostDto.builder().title(vo.getTitle()).content(vo.getContent()).imgUrl(imgUrl).build();
        repository.save(dto);
    }

    public List<PostDto> list (String type) {
        String cmsType = StringUtil.isEmpty(type) ? GlobalCode.TYPE_SINGLE.getCode() : type; //기본값
        return repository.findByTypeContaining(cmsType);
    }


}
