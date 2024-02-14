package com.cms.world.stepper.domain;


import com.cms.world.apply.domain.CmsApplyDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Table(name="cms_tm_log")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class StepperDto {

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
