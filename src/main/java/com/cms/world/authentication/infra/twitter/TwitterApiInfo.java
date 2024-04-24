package com.cms.world.authentication.infra.twitter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class TwitterApiInfo {

    @Value("${twitter.consumerKey}")
    private String apiKey;

    @Value("${twitter.consumerSecret}")
    private String apiSecret;

    @Value("${twitter.redirectUrl}")
    private String callbackUrl;
}
