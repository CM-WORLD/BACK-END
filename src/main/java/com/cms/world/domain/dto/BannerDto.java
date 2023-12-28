package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="banner")
@Getter
@Setter
public class BannerDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name ="IMG_URL", nullable = false)
    private String ImgUrl;

    @Column(name ="HREF_URL", nullable = false)
    private String hrefUrl;

    @Column(name ="CMMNT")
    private String comment;

    @Column(name ="STRT_DT")
    private String startDate;

    @Column(name ="END_DT")
    private String endDate;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;

    @Column(name = "UPT_DT")
    @UpdateTimestamp
    private String uptDate;
}
