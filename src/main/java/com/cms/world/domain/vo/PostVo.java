package com.cms.world.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class PostVo {

    private Long id;
    private String title;
    private String content;
    private String type; //커미션 타입
    private MultipartFile img; //dto와 다른 부분.
    private String reg_date;

    @Builder
    public PostVo (String title, String content, MultipartFile img, String type,String reg_date) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.type = type;
        this.reg_date = reg_date;
    }
}
