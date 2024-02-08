package com.cms.world.authentication.infra.kakao;


import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthProvider;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    private long id;

    private String accessToken;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
        private String profile_image_url;
        private String thumbnail_image_url;
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getProfileImg() {
        if (StringUtil.isEmpty(kakaoAccount.profile.profile_image_url)) {
            return kakaoAccount.profile.thumbnail_image_url;
        }
        return kakaoAccount.profile.profile_image_url;
    }

    @Override
    public String  getOAuthProvider() {
        return GlobalCode.OAUTH_KAKAO.getCode();
    }
}