package com.cms.world.payment.domain;


import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.SnowflakeIdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "APLY_ID", referencedColumnName = "ID")
    private ApplyDto applyDto;

    @Schema(description = "결제 제목", example = "뭉이커미션기본금")
    @Column(name = "TITLE")
    private String title;

    @Schema(description = "결제 금액", example = "1000")
    @Column(name = "AMOUNT")
    private int amount;

    @Schema(description = "결제 상태", example = "결제완료")
    @Column(name = "STATUS")
    private String status;

    @Schema(description = "결제 수단", example = "신용카드")
    @Column(name = "PYMT_MTHD")
    private String paymentMethod;

    @Schema(description = "어드민의 메세지", example = "금액 설명")
    @Column(name = "MSG")
    private String message;

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
