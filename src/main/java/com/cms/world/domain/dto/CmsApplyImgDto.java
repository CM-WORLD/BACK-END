package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "cms_img")
@Getter
@Setter
public class CmsApplyImgDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", columnDefinition = "varchar(100)")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "APLY_ID", referencedColumnName = "ID") //name은 설정할 fk 이름이다.
    private CmsApplyDto applyDto;

    @Column(name = "IMG_URL", nullable = false, columnDefinition = "varchar(100)")
    private String imgUrl;
    
    @Column(name = "TP_CD")
    private String type; // 신청이미지 / 완료 이미지

    @Column(name = "RGTR_DT")
    private String regDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

}
