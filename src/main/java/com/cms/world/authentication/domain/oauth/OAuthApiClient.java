package com.cms.world.authentication.domain.oauth;

public interface OAuthApiClient {

    String oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
