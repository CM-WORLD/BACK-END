package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


/* 커미션 타입 vo */
@Getter
@Setter
@ToString
public class CommissionVo {

    private String name; //커미션 이름
    private String content;
    private MultipartFile profileImg;
    private String status; // 사용 여부 상태
}
