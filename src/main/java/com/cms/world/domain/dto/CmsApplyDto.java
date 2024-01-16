package com.cms.world.domain.dto;

import com.cms.world.utils.GlobalCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "CMS_ID", referencedColumnName = "ID")
    private CommissionDto cmsDto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEM_ID", referencedColumnName = "ID")
    private MemberDto memberDto;

    @OneToOne(mappedBy = "applyDto")
    private CmsPayDto cmsPayDto;


    @Column(name = "TP_CD")
    private String cmsType;

    @Transient
    private String cmsTypeNm;

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
        this.setRegDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
    }

    @PostLoad
    public void doLoad () {
        this.setStatusNm(GlobalCode.getDescByCode(this.getStatus()));
        this.setCmsTypeNm(GlobalCode.getDescByCode(this.getCmsType()));
    }

}
