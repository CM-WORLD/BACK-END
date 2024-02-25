package com.cms.world.stepper.domain;


import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.common.util.DateUtil;
import com.cms.world.common.code.GlobalCode;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "탐라 메세지, 로그 남길 때 추가 정보 같이 입력해서 보여주기용", example = "6000원이 결제되었습니다.")
    @Column(name = "MSG", nullable = false)
    private String message;

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
