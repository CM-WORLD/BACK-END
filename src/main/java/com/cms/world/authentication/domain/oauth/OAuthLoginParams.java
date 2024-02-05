package com.cms.world.authentication.domain.oauth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    String oAuthProvider();
    MultiValueMap<String, String> makeBody();


}
