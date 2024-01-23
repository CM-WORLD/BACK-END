package com.cms.world.domain.vo;


import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardVo {

    public interface TitleCheck {}
    public interface ContentCheck {}
    public interface ImgListCheck {}

    @GroupSequence({TitleCheck.class, ContentCheck.class, ImgListCheck.class})
    public interface BoardVoCheckSequence {}

    @NotEmpty(message = "{empty.title}", groups = TitleCheck.class)
    @Size(min = 5, max = 30, message = "{invalid.title}", groups = TitleCheck.class)
    private String title;

    @NotEmpty(message = "{empty.content}", groups = ContentCheck.class)
    @Size(min = 30, max = 200, message = "{invalid.content}", groups = ContentCheck.class)
    private String content;
    private String bbsCode;
    private Long memberId;

    @NotEmpty(message = "{empty.img}", groups = ImgListCheck.class)
    private List<MultipartFile> imgList;
}
