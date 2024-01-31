package com.cms.world.authentication.infra.naver;

import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String nickname;
        private String profile_img;
    }

//    @Override
//    public String getEmail() {
//        return response.email;
//    }

    @Override
    public String getProfileImg() {
        return response.profile_img;
        //TODO:: profile_img 항목값 맞는지 체크
    }

    @Override
    public long getId() {
        return 100;
    }

    @Override
    public String getNickname() {
        return response.nickname;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }
}
