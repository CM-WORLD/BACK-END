package com.cms.world.domain.vo;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class ReviewVo {

    @NotEmpty(message = "{empty.content}")
    @Size(min = 10, max = 500, message = "{invalid.content}")
    private String content;

    private List<MultipartFile> imgList;

    private String cmsApplyId;
    private String nickName;
    private String cmsId;
}
