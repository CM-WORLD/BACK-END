package com.cms.world.stepper.domain;


import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private ApplyDto applyDto;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Transient
    private String statusNm;

    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
    }

    @PostLoad
    public void doLoad () {
        this.setStatusNm(GlobalCode.getDescByCode(this.getStatus()));
    }
}
