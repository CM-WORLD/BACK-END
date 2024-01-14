package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="bbs_reply")
@Getter
@Setter
public class ReplyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "BBS_ID", referencedColumnName = "ID")
    private BoardDto boardDto;

    @Column(name = "NICK_NM", nullable = false)
    private String nickName; // nickName

    @Column(name = "PRNT_ID")
    private Long parentId; // 대댓글 구현 : 부모 id

    @Column(name = "DEPTH")
    private Long depth;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;

    @Column(name = "UPT_DT")
    @UpdateTimestamp
    private String uptDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
    }
}
