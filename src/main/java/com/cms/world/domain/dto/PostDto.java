package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "post")
@Getter
@Setter
public class PostDto {

    public PostDto() {

    }

    @Builder
    public PostDto(String title, String content, String imgUrl, String type) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="TITLE", nullable = false)
    private String title;

    @Column(name="CONTENT", nullable = false)
    private String content;

    @Column(name="IMG_URL", nullable = false)
    private String imgUrl;

    @Column(name="TP_CD")
    private String type; // 커미션 타입 코드
    
    @Column(name="BG_YN")
    private String isNotBg; // 배경 유무

    @Column(name="RGTR_DT")
    private String regDate;

    @PrePersist
    public void onPrePersist() { // 디비에 넣기 전에 현재 시각 날짜를 format해서 insert
        this.regDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        if (StringUtil.isEmpty(this.type)) this.type = GlobalCode.TYPE_SINGLE.getCode(); // 타입 코드가 없으면 1인 기본 설정
    }
}
