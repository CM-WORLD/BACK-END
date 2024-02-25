package com.cms.world.apply.domain;

import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.cms.CommissionDto;
import com.cms.world.common.util.DateUtil;
import com.cms.world.common.code.GlobalCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="apply")
@Getter
@Setter
@ToString
public class ApplyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CMS_ID", referencedColumnName = "ID")
    private CommissionDto cmsDto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEM_ID", referencedColumnName = "ID")
    private MemberDto memberDto;


    @Column(name = "TP_CD")
    private String cmsType;

    @Column(name = "TITLE", nullable = false)
    @Length(min = 2, max = 2000)
    private String title;

    @Column(name = "CONTENT", nullable = false, length = 1000)
    @Length(min = 5, max = 2000)
    private String content;

    @Column(name = "ACC_NM", nullable = false)
    private String bankOwner;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "RGTR_DT")
    private String regDate;

    @Transient
    private String cmsTypeNm;

    @Transient
    private String statusNm;

    @Transient
    private String cmsName;

    @PrePersist
    public void doPersist () {
        this.setStatus(GlobalCode.CMS_PENDING.getCode());
        this.setStatusNm(GlobalCode.CMS_PENDING.getDesc());
        this.setRegDate(DateUtil.currentDateTime());
    }

    @PostLoad
    public void doLoad () {
        this.setStatusNm(GlobalCode.getDescByCode(this.getStatus()));
        this.setCmsTypeNm(GlobalCode.getDescByCode(this.getCmsType()));
    }

}
