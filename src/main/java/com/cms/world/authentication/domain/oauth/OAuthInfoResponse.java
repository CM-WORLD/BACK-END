package com.cms.world.authentication.domain.oauth;

public interface OAuthInfoResponse {
//    String getEmail();

    String getId();

    String getNickname();
    String getProfileImg();

    String getOAuthProvider();

    String getAccessToken();
}
