package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class BannerVo {

    private MultipartFile img;
    private String hrefUrl;
    private String comment;
    private String startDate;
    private String endDate;
    private String delYn;
}
