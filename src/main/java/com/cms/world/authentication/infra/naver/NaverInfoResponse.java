package com.cms.world.authentication.infra.naver;

import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthProvider;
import com.cms.world.utils.GlobalCode;
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
        private String id; // naver id 유니크 값
        private String email;
        private String nickname;
        private String profile_image;
        
    }

//    @Override
//    public String getEmail() {
//        return response.email;
//    }

    @Override
    public String getProfileImg() {
        return response.profile_image;
        //TODO:: profile_img 항목값 맞는지 체크
    }

    @Override
    public String getId() {
        return response.id;
    }

    @Override
    public String getNickname() {
        return response.nickname;
    }

    @Override
    public String getOAuthProvider() {
        return GlobalCode.OAUTH_NAVER.getCode();
    }
}
