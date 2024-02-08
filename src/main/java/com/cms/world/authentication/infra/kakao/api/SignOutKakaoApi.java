package com.cms.world.authentication.infra.kakao.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoSignOut", url= "${kakao.signOutUrl}")
public interface SignOutKakaoApi {

    @PostMapping(value = "/", produces = "application/json")
    Long deleteToken(@RequestHeader("Authorization") String accessToken);

}
