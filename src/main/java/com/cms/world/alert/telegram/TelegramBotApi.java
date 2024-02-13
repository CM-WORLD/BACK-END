package com.cms.world.alert.telegram;


import com.cms.world.alert.AlertMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotApi {

    @Value("${telegram.notification.enabled}")
    private boolean isEnabled;

    @Value("${telegram.notification.bot.token}")
    private String botToken;

    @Value("${telegram.notification.chat.id}")
    private String chatId;

    private final ChatBotApi chatBotApi;

    public String formatMessage (AlertMsg alertMsg) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public void sendAlertToAdmin (String formattedMessage) {
        chatBotApi.sendMessage(botToken, formattedMessage, chatId);
    }
}
