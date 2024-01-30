package com.cms.world.authentication.domain.oauth;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
