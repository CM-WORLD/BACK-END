package com.cms.world.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "cms_pay")
@Getter
@Setter
public class CmsPayDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "APLY_ID", referencedColumnName = "ID") //name은 설정할 fk 이름이다.
    private CmsApplyDto applyDto;

    //선택사항
    @Column(name = "PAY_AMT")
    private Double payAmt;

    @Column(name = "CMMNT")
    private String comment;

    @Column(name = "END_DT")
    private LocalDateTime endDate;

    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
    }
}
