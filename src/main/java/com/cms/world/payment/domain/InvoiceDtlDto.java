package com.cms.world.payment.domain;

import com.cms.world.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inv_dtl")
@Getter
@Setter
@Tag(name = "InvoiceDtlDto", description = "인보이스 상세 정보")
public class InvoiceDtlDto {

    @Schema(description = "인보이스 아이템 ID", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Schema(description = "인보이스 요청서 ID")
    @JsonIgnoreProperties({"invoiceDtlDtoList"})
    @ManyToOne
    @JoinColumn(name = "INV_ID", referencedColumnName = "ID")
    private InvoiceDto invoiceDto;

    @Schema(description = "인보이스 아이템 이름", example = "장식 추가금")
    @Column(name = "NAME")
    private String name;

    @Schema(description = "인보이스 아이템 금액", example = "1000")
    @Column(name = "AMOUNT")
    private Double amount;

    @Schema(description = "인보이스 아이템 추가일", example = "2021-10-01 12:00:00")
    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
    }
}
