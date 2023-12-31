package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="board")
@Getter
@Setter
public class BoardDto {

    public BoardDto() {}


    @Builder
    public BoardDto(String title, String content, String writer, String type) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "WRTR", nullable = false)
    private String writer;

    @Column(name = "TYPE", nullable = false)
    private String type; //게시판 타입

    @Column(name = "VW_CNT")
    private int viewCnt;

    @Column(name = "RGTR_DT")
    private String regDate;

    @Column(name = "UPT_DT")
    @UpdateTimestamp
    private String uptDate;

    @PrePersist
    public void doPersist () {
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        if(StringUtil.isEmpty(type)) {
            this.type = GlobalCode.BBS_APLY.getCode();
        }

    }
}
