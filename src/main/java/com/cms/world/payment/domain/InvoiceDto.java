package com.cms.world.payment.domain;

import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalCode;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@Tag(name = "InvoiceDto", description = "인보이스")
public class InvoiceDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Schema(description = "커미션 신청서 ID")
    @ManyToOne
    @JoinColumn(name = "APLY_ID", referencedColumnName = "ID")
    private CmsApplyDto applyDto;

    @Schema(description = "인보이스 요청 총 금액", example = "1000")
    @Column(name = "AMOUNT")
    private Double amount;

    @Schema(description = "작가 코멘트")
    @Column(name = "CMMNT")
    private String comment;

    @Schema(description = "인보이스 생성일", example = "2021-10-01 12:00:00")
    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
    }


}
