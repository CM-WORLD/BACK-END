package com.cms.world.alert;


import com.cms.world.alert.AlertMsg;
import com.cms.world.alert.telegram.ChatBotApi;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    /* telegram봇으로 어드민에게 메세지 전송하기*/
    public void sendAlertToAdmin (String formattedMessage) {
        chatBotApi.sendMessage(botToken, formattedMessage, chatId);
    }

    /* 계좌이체 알림을 어드민에게 전송한다. */
    public String bankTransferAlert (String applyTitle, String applyId, String paymentId) {
        // 해당 결제 아이디의 상태를 PAYMENT_BANK_PROCESS로 변경한다.

        // 결제 아이디 조회
        // 결제가 없으면 throw new Exceptoin()


        AlertMsg alertMsg = AlertMsg.builder()
                .paymentStatus(GlobalCode.PAYMENT_BANK_PROCESS.getCode())
                .applyTitle(applyTitle)
                .applyId(applyId)
                .receiverNickName("걍진")
                .build();
        String messageStr = alertMsg.createBankTransferAlertMsg();
        sendAlertToAdmin(messageStr);

        return GlobalStatus.SUCCESS.getMsg();
    }

}
