package com.cms.world.apply.domain;


import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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
    
    @Column(name = "STATUS")
    private String status; // 신청이미지 / 완료 이미지

    @Column(name = "DEL_YN")
    private String delYn; //삭제 여부

    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPrePersist () {
        this.setStatus(GlobalCode.APPLIED_IMG.getCode());
        this.setDelYn("N");
        this.setRegDate(DateUtil.currentDateTime());
    }

}
