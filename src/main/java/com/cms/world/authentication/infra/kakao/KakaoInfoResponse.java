package com.cms.world.authentication.infra.kakao;


import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthProvider;
import com.cms.world.utils.GlobalCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
    }

//    @Override
//    public String getEmail() {
//        return kakaoAccount.email;
//    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public String getProfileImg() {
        return "kakao.jpg";
    }

    @Override
    public String  getOAuthProvider() {
        return GlobalCode.OAUTH_KAKAO.getCode();
    }
}