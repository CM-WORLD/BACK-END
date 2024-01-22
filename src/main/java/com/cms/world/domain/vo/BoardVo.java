package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardVo {

    private String title;
    private String content;
    private String bbsCode;
    private Long memberId;

    private List<MultipartFile> imgList; //게시물 이미지 리스트
}
