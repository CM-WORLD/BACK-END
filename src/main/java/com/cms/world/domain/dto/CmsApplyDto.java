package com.cms.world.domain.dto;

import com.cms.world.utils.GlobalCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="cms_aply")
@Getter
@Setter
public class CmsApplyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "CMS_ID", referencedColumnName = "ID")
    private CommissionDto cmsDto;

    @ManyToOne
    @JoinColumn(name = "MEM_ID", referencedColumnName = "ID")
    private MemberDto memberDto;

    @Column(name = "TP_CD") // 1인이냐 2인이냐
    private String cmsType;

    @Column(name = "TITLE", nullable = false)
    @Length(min = 2, max = 2000)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    @Length(min = 5, max = 2000)
    private String content;

    @Column(name = "ACC_NM", nullable = false)
    private String bankOwner;

    @Column(name = "STATUS")
    private String status;

    @Transient
    private String statusNm;

    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        this.setStatus(GlobalCode.PAY_PENDING.getCode());
        this.setStatusNm(GlobalCode.PAY_PENDING.getDesc());
        this.setRegDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm")));
    }

    @PostLoad
    public void doLoad () {
        this.setStatusNm(GlobalCode.getDescByCode(this.getStatus()));
    }

}
