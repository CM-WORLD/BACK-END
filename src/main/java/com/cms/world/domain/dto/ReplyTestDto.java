package com.cms.world.domain.dto;


import com.cms.world.utils.DateUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="tmp_reply")
@Getter
@Setter
public class ReplyTestDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "PID")
    private Long parentId; 
    
    @Column(name = "PGP")
    private String group; //대댓글 경로

    @Column(name = "RGTR_DT")
    @CreatedDate
    private LocalDateTime regDate;

}
