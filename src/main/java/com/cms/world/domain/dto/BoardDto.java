package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="board")
@Getter
@Setter
public class BoardDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BBS_CD")
    private String bbsCode;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "NICK_NM", nullable = false)
    private String nickName; // 사용자 닉네임

    @Column(name = "VW_CNT")
    private int viewCnt;

    @Column(name = "DSPY_YN")
    private String displayYn; // 후기 비공개 여부

    @Column(name = "DEL_YN")
    private String delYn; // 게시글 삭제 여부

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;

    @Column(name = "UPT_DT")
    @UpdateTimestamp
    private String uptDate;

    @PrePersist
    public void doPersist () {
        if(StringUtil.isEmpty(bbsCode)) {
            this.setBbsCode(GlobalCode.BBS_APLY.getCode());
        }
        if(StringUtil.isEmpty(delYn)) {
            this.setDelYn("N");
        }
        if(StringUtil.isEmpty(displayYn)) {
            this.setDisplayYn("Y");
        }

        this.setRegDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
    }
}
