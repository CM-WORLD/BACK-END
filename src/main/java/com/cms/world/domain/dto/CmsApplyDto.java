package com.cms.world.domain.dto;

import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

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
    @JoinColumn(name = "CMS_ID", referencedColumnName = "ID")
    private CommissionDto cmsDto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEM_ID", referencedColumnName = "ID")
    private MemberDto memberDto;

    @OneToOne(mappedBy = "applyDto")
    private CmsPayDto cmsPayDto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applyDto")
    private List<CmsApplyImgDto> cmsApplyImgDto;

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

    @Transient
    private String cmsName;

    @PrePersist
    public void doPersist () {
        this.setStatus(GlobalCode.PAY_PENDING.getCode());
        this.setStatusNm(GlobalCode.PAY_PENDING.getDesc());
        this.setRegDate(DateUtil.currentDateTime());
    }

    @PostLoad
    public void doLoad () {
        this.setStatusNm(GlobalCode.getDescByCode(this.getStatus()));
        this.setCmsTypeNm(GlobalCode.getDescByCode(this.getCmsType()));
    }

}
