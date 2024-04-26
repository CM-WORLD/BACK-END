package com.cms.world.pymthstr.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pymt_hstr")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PymtHstrEntity {

    @Column(name = "pymt_dt", nullable = false)
    private String paymentDate;

    @Id
    @Column(name = "pymt_id", nullable = false)
    private Long id;

    @Column(name = "user_nm", nullable = false)
    private String username;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "pymt_mthd", nullable = false)
    private String paymentMethod;

    @Column(name = "pymt_mthd_nm", nullable = false)
    private String paymentMethodName;

    @Builder
    public PymtHstrEntity(String paymentDate, Long id, String username, Long amount, String paymentMethod, String paymentMethodName) {
        this.paymentDate = paymentDate;
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentMethodName = paymentMethodName;
    }
}
