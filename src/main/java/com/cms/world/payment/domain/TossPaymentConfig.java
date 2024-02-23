package com.cms.world.payment.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TossPaymentConfig {

    @Value("${toss.clientId}")
    private String clientId;

    @Value("${toss.clientSecret}")
    private String clientSecret;

    @Value("${toss.successUrl}")
    private String successUrl;

    @Value("${toss.failUrl}")
    private String failUrl;

    @Value("${toss.baseUrl}")
    private String baseUrl;
    // 예제의 private static final String URL = "https://api.tosspayments.com/v1/payments";


}
