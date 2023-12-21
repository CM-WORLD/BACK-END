package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Table(name="cms_log")
@Entity
@Getter
@Setter
public class TimeLogDto {

    public TimeLogDto () {

    }

    @Builder
    public TimeLogDto (String status, CmsApplyDto applyDto) {
        this.status = status;
        this.applyDto = applyDto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CMS_ID", referencedColumnName = "ID")
    private CmsApplyDto applyDto;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "RGTR_DT")
    private String regDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
}
