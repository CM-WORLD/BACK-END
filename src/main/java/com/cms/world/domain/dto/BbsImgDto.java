package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="bbs_img")
@Getter
@Setter
public class BbsImgDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "BBS_ID", referencedColumnName = "ID")
    private BoardDto boardDto;

    @Column(name ="IMG_URL")
    private String imgUrl;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;
}
