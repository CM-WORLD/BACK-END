package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "cms_aply_img")
@Getter
@Setter
public class CmsApplyImgDto {

    public CmsApplyImgDto () {

    }

    @Builder
    public CmsApplyImgDto (CmsApplyDto applyDto, String img_url){
        this.applyDto = applyDto;
        this.img_url = img_url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", columnDefinition = "varchar(20)")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "CMS_ID", referencedColumnName = "ID") //name은 설정할 fk 이름이다.

    private CmsApplyDto applyDto;

    @Column(name = "IMG_URL", nullable = false, columnDefinition = "varchar(100)")
    private String img_url;

    @Column(name = "RGTR_DT")
    private String reg_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

}
