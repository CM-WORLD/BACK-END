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
public class BoardVo {

    @NotEmpty(message = "{empty.title}")
    @Size(min = 5, max = 30, message = "제목은 5자 이상 30자 이하로 입력해주세요.")
    private String title;

    @Size(min = 30, max = 200, message = "내용은 30자 이상 200자 이하로 입력해주세요.")
    private String content;
    private String bbsCode;
    private Long memberId;

    @NotEmpty(message = "이미지를 1개 이상 등록해주세요.")
    private List<MultipartFile> imgList;
}
