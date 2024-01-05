package com.cms.world.domain.dto;


import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @JoinColumn(name = "APLY_ID", referencedColumnName = "ID")
    private CmsApplyDto applyDto;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "NICK_NM", nullable = false)
    private String nickName;

    @Column(name = "DSPY_YN")
    private String displayYn;

    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        if (StringUtil.isEmpty(displayYn)) {
            this.setDisplayYn("N");
        }
    }

}
