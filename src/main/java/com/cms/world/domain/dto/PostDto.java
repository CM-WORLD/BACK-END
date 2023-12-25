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
    public PostDto(String TITLE, String CONTENT, String IMG_URL, String TP_CD) {
        this.TITLE = TITLE;
        this.CONTENT = CONTENT;
        this.IMG_URL = IMG_URL;
        this.TP_CD = TP_CD;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;

    @Column(nullable = false)
    private String TITLE;

    @Column(nullable = false)
    private String CONTENT;

    @Column(nullable = false)
    private String IMG_URL;

    @Column
    private String TP_CD; // 커미션 타입 코드
    
    @Column 
    private String BG_YN; // 배경 유무

    @Column
    private String RGTR_DT;

    @PrePersist
    public void onPrePersist() { // 디비에 넣기 전에 현재 시각 날짜를 format해서 insert
        this.RGTR_DT = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        if (StringUtil.isEmpty(this.TP_CD)) this.TP_CD = GlobalCode.TYPE_SINGLE.getCode(); // 타입 코드가 없으면 1인 기본 설정
    }
}
