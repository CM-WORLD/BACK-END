package com.cms.world.payment.api;

import com.cms.world.payment.domain.TossConfirmVo;
import com.cms.world.payment.domain.TossPaymentVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "toss-payment", url = "${toss.successConfirmUrl}")

public interface TossConfirmApi {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TossPaymentVo confirmPayment(@RequestHeader("Authorization") String basicAuthCode, @RequestBody TossConfirmVo paymentConfirmVO);
}

