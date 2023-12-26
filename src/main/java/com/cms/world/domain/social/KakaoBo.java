package com.cms.world.domain.social;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
//@NoArgsConstructor //이거 있으면 안된다;;
@Getter
@Setter
public class KakaoBo {

    public KakaoBo(@Value("${kakao.clientId}") String clientId,
                   @Value("${kakao.redirectUrl}") String redirectUrl,
                   @Value("${kakao.clientSecret}") String clientSecret,
                   @Value("${kakao.authUrl}") String authUrl,
                   @Value("${kakao.send.msgUrl}") String sendMsgUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.authUrl = authUrl;
        this.sendMsgUrl = sendMsgUrl;
    }

    private String clientId;
    private String redirectUrl;
    private String clientSecret;
    private String authUrl;
    private String sendMsgUrl;

}

