package com.cms.world.payment.service;


import com.cms.world.apply.repository.CmsApplyRepository;
import com.cms.world.payment.api.TossConfirmApi;
import com.cms.world.payment.domain.PaymentVo;
import com.cms.world.payment.domain.TossConfirmVo;
import com.cms.world.payment.domain.TossPaymentConfig;
import com.cms.world.payment.domain.TossPaymentVo;
import com.cms.world.payment.repository.PaymentRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PaymentService {


    private final PaymentRepository repository;
    private final TossConfirmApi confirmApi;

    private final TossPaymentConfig tossPaymentConfig;

    public PaymentService(PaymentRepository repository, TossConfirmApi confirmApi, TossPaymentConfig tossPaymentConfig) {
        this.repository = repository;
        this.confirmApi = confirmApi;
        this.tossPaymentConfig = tossPaymentConfig;
    }

    public TossPaymentVo confirmPayment(TossConfirmVo paymentConfirmVO) {

        String secretValue = tossPaymentConfig.getClientSecret() + ":";
        String basicAuth = Base64.getEncoder().encodeToString(secretValue.getBytes());

        TossPaymentVo paymentResult = confirmApi.confirmPayment(
                "Basic " + basicAuth,
                paymentConfirmVO
        );
        return paymentResult;
    }
}
