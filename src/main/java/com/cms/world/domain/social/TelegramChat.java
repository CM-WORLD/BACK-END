package com.cms.world.domain.social;


import com.cms.world.domain.common.AlertMsg;
import com.cms.world.domain.vo.CmsApplyVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class TelegramChat {

    private Logger logger = LoggerFactory.getLogger(TelegramChat.class);

    @Value("${telegram.notification.enabled}")
    private boolean isEnabled;

    @Value("${telegram.notification.bot.token}")
    private String botToken;

    @Value("${telegram.notification.chat.id}")
    private String chatId;

    private AlertMsg alertMsg;

    public TelegramChat (AlertMsg alertMsg) {
        this.alertMsg = alertMsg;
    }
    

    public void sendAlert (AlertMsg alertMsg) {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
//
        try {
            String param = new Gson().toJson(alertMsg);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> entity = new HttpEntity<>(param, headers); // <> 타입 에러 발생
            restTemplate.postForEntity(url, entity, String.class);
        } catch (Exception e) {
            logger.error("telegram alert send failed", e);
        }
    }
}
