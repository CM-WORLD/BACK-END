package com.cms.world.payment.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pymt_hstr")
@Getter
@Setter
@Tag(name = "PayHstrDto", description = "결제 이력 정보")
public class PayHstrDto {

    @Schema(description = "결제 이력 ID", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Schema(description = "결제 ID")
    @ManyToOne
    @JoinColumn(name = "PYMT_ID", referencedColumnName = "ID")
    private PaymentDto paymentDto;

}
