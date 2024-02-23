package com.cms.world.payment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPaymentVo {

    private String orderName;
    private String method;
    private String totalAmount;
    private Card card;
//    private VirtualAccount virtualAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Card{
        private String approveNo;
    }
}


