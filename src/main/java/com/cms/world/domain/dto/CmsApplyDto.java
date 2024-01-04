package com.cms.world.domain.dto;

import com.cms.world.utils.GlobalCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="cms_aply")
@Getter
@Setter
public class CmsApplyDto {

    //필수사항
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

//    @ManyToOne
//    @JoinColumn(name = "CMS_ID", referencedColumnName = "ID")
//    private CommissionDto cmsDto;

    @Column(name = "TP_CD") // 1인이냐 2인이냐
    private String cmsType;

    @Column(name = "TITLE", nullable = false)
    @Length(min = 2, max = 2000)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    @Length(min = 5, max = 2000)
    private String content;

    /* 비회원/회원을 어떻게 처리하지...? */
    @Column(name = "NICK_NM", nullable = false)
    private String nickName; //사용자 이름

    @Column(name = "ACC_NM", nullable = false)
    private String bankOwner;

    @Column(name = "DPSIT_YN")
    private String depositYn;

    @Column(name = "STATUS") // 커미션 프로세스  + 예약
    private String status ;

    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        this.setStatus(GlobalCode.PAY_PENDING.getCode());
        this.setDepositYn("N");
        this.setRegDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm")));
    }

}
