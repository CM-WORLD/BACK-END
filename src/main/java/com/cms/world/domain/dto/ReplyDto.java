package com.cms.world.domain.dto;


import com.cms.world.utils.DateUtil;
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

    @Column(name = "")
    private Long parentId; 
    
    @Column(name = "DEPTH")
    private String depthPath; //대댓글 경로

    @Column(name = "RGTR_DT")
    private String regDate;

    @Column(name = "UPT_DT")
    private String uptDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
    }
}
