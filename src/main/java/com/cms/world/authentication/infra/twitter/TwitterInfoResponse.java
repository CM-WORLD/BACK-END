package com.cms.world.authentication.infra.twitter;


import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.social.twitter.api.TwitterProfile;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterInfoResponse implements OAuthInfoResponse {

//    @JsonProperty("kakao_account")
    private TwitterProfile twitterProfile;

//    @Getter
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    static class KakaoAccount {
//        private KakaoProfile profile;
//        private String email;
//    }
//
//    @Getter
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    static class KakaoProfile {
//        private String nickname;
//    }

    @Override
    public String getNickname() {
        return twitterProfile.getName();
    }

    @Override
    public String getProfileImg() {
        return twitterProfile.getProfileImageUrl();
    }

    @Override
    public long getId() {
        return twitterProfile.getId();
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}