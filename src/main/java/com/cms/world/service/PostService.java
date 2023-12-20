package com.cms.world.service;


import com.cms.world.domain.dto.PostDto;
import com.cms.world.domain.vo.PostVo;
import com.cms.world.repository.PostRepository;
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

    public void add(PostVo vo) throws IOException {
        String imgUrl = s3UploadService.saveFile(vo.getImg());
        PostDto dto = PostDto.builder().TITLE(vo.getTitle()).CONTENT(vo.getContent()).IMG_URL(imgUrl).build();
        repository.save(dto);
    }

    public List<PostDto> list () {
        return repository.findAll();
    }


}
