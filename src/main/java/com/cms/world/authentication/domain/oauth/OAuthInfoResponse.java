package com.cms.world.authentication.domain.oauth;

public interface OAuthInfoResponse {

    String getEmail();

    Long getUid();

    OAuthProvider getOAuthProvider();
}
