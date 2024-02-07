package com.cms.world.apply.domain;


import com.cms.world.domain.vo.BoardVo;
import jakarta.validation.GroupSequence;
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
public class CmsApplyVo {

    public interface TitleCheck {}
    public interface ContentCheck {}

    public interface bankOwnerCheck {}
    public interface ImgListCheck {}

    private String cmsId; //신청하려는 커미션 아이디
    private Long userId; //신청자 아이디
    private String status;

    @GroupSequence({TitleCheck.class, ContentCheck.class, bankOwnerCheck.class, ImgListCheck.class})
    public interface CmsApplyVoSequence {}

    @NotEmpty(message = "{empty.title}", groups = TitleCheck.class)
    @Size(min = 5, max = 30, message = "{invalid.title}", groups = TitleCheck.class)
    private String title;

    @NotEmpty(message = "{empty.content}", groups = ContentCheck.class)
    @Size(min = 10, max = 1000, message = "{invalid.content}", groups = ContentCheck.class)
    private String content;

    @NotEmpty(message = "{empty.bankOwner}", groups = bankOwnerCheck.class)
    private String bankOwner;

    @NotEmpty(message = "{empty.img}", groups = ImgListCheck.class)
    private List<MultipartFile> imgList;
}