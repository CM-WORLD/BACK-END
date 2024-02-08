package com.cms.world.authentication.infra.naver.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "naverSignOut", url = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=${naver.clientId}&client_secret=${naver.clientSecret}")
public interface DeleteTokenApi {

    //&access_token=${}&service_provider=NAVER
    @PostMapping
    Map<String, Object> deleteToken(@RequestParam("access_token") String accessToken,
                                    @RequestParam("service_provider") String provider);
}
