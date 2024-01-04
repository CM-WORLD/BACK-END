package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class CmsApplyVo {

    private String status;
    private String content;
    private String nickName;
    private String bankOwner;
    private List<MultipartFile> imgList;
}
