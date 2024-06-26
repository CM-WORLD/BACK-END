package com.cms.world.payment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class PaymentVo {

    private String applyId;
    private Double payAmt;
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
