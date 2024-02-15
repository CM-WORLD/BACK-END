package com.cms.world.payment.domain;


import com.cms.world.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment")
@Getter
@Setter
@Tag(name = "PaymentDto", description = "결제 정보")
public class PaymentDto {

    @Schema(description = "결제 ID", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Schema(description = "결제 금액", example = "1000")
    @Column(name = "AMOUNT")
    private Double amount;

    @Schema(description = "결제 상태", example = "결제완료")
    @Column(name = "STATUS")
    private String status;

    @Schema(description = "결제 수단", example = "신용카드")
    @Column(name = "PYMT_MTHD")
    private String paymentMethod;

    /* 조회를 위해 memberId를 넣지만, fk는 걸지 않는다. 회원과 관계없이 유지되는 데이터 */
    @Schema(description = "결제자 ID", example = "1")
    @Column(name = "MMBER_ID")
    private String memberId;

    @Schema(description = "결제 일시", example = "2021-10-01 12:00:00")
    @Column(name = "RGTR_DT")
    private String regDate;
    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
    }
}
