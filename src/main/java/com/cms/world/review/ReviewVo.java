package com.cms.world.review;


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
public class ReviewVo {

    public interface ContentCheck {}
    public interface ImgListCheck {}

    @GroupSequence({ReviewVo.ContentCheck.class, ReviewVo.ImgListCheck.class})
    public interface ReviewVoCheckSequence {}

    @NotEmpty(message = "{empty.content}", groups = ReviewVo.ContentCheck.class)
    @Size(min = 10, max = 500, message = "{invalid.content}", groups = ReviewVo.ContentCheck.class)
    private String content;

    private List<MultipartFile> imgList;

    private String cmsApplyId;
    private String nickName;
    private String cmsId;
}
