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

    /* 전체/타입별 리스트 조회 */
    public List<PostDto> list (String type) {
        if(StringUtil.isEmpty(type) || type.equals("ALL")) return repository.findAll();

        return repository.findByTypeContaining(type);
    }


}
