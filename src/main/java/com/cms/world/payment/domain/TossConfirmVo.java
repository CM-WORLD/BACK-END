package com.cms.world.payment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TossConfirmVo {

private String paymentType;
private String paymentKey;
private String orderId;
private Integer amount;
}