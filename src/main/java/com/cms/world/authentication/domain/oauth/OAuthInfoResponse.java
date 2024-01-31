package com.cms.world.authentication.domain.oauth;

public interface OAuthInfoResponse {
//    String getEmail();

    long getId();

    String getNickname();
    String getProfileImg();


    OAuthProvider getOAuthProvider();
}
