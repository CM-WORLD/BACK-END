package com.cms.world.alert;


import com.cms.world.alert.telegram.ChatBotApi;
import com.cms.world.payment.domain.PaymentDto;
import com.cms.world.payment.repository.PaymentRepository;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertService {

    @Value("${telegram.notification.enabled}")
    private boolean isEnabled;

    @Value("${telegram.notification.bot.token}")
    private String botToken;

    @Value("${telegram.notification.chat.id}")
    private String chatId;

    private final ChatBotApi chatBotApi;

    private final PaymentRepository payRepository;

    /* telegram봇으로 어드민에게 메세지 전송하기*/
    public void sendAlertToAdmin (String formattedMessage) {
        chatBotApi.sendMessage(botToken, formattedMessage, chatId);
    }

    /* 계좌이체 알림을 어드민에게 전송한다. */
    public String bankTransferAlert (String applyTitle,
                                     String applyId,
                                     Long paymentId) throws Exception {

        // 결제 아이디 조회
        Optional<PaymentDto> paymentDto = payRepository.findById(paymentId);
        // 결제가 없으면 throw new Exceptoin()
        if(!paymentDto.isPresent()) throw new Exception("해당 결제 ID가 없습니다.");
        // 해당 결제 아이디의 상태를 PAYMENT_BANK_PROCESS로 변경한다.
        PaymentDto payment = paymentDto.get();
        payment.setStatus(GlobalCode.PAYMENT_BANK_PROCESS.getCode());
        payRepository.save(payment);

        AlertMsg alertMsg = AlertMsg.builder()
                .paymentStatus(payment.getStatusNm())
                .applyTitle(applyTitle)
                .applyId(applyId)
                .receiverNickName("걍진")
                .build();
        String messageStr = alertMsg.createBankTransferAlertMsg();
        sendAlertToAdmin(messageStr);

        return GlobalStatus.SUCCESS.getMsg();
    }

}
