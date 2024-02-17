package com.cms.world.domain.dto;


import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cms_rvw")
@Getter
@Setter
public class ReviewDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "APLY_ID", referencedColumnName = "ID") // one to one test ok
    private ApplyDto applyDto;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    /* 성능 개선 + 마이리뷰용 닉네임 (닉네임이 작성자 대신 보이게 하려고 id 대신) */
    @Column(name = "NICK_NM")
    private String nickName;

    @Column(name = "MEM_ID", nullable = false)
    private Long memberId;

    /* 성능 개선 + 커미션 통계용 cmsId */
    @Column(name = "CMS_ID", nullable = false)
    private String cmsId;

    @Column(name = "DSPY_YN")
    private String displayYn;

    @Column(name = "RGTR_DT")
    private String regDate;

    @Transient
    private String cmsName;

    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
        if (StringUtil.isEmpty(displayYn)) {
            this.setDisplayYn("Y");
        }
    }

    @PostLoad
    public void doLoad () {
        if (applyDto != null) {
            this.setCmsName(applyDto.getCmsDto().getName());
        }
    }
}
