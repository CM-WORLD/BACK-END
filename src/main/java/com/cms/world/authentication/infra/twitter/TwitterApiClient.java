package com.cms.world.authentication.infra.twitter;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TwitterApiClient {

    private final TwitterApiInfo twitterApiInfo;
    private TwitterConnectionFactory connectionFactory;

    @PostConstruct
    private void init() {
        // TwitterConnectionFactory 인스턴스 초기화
        this.connectionFactory = new TwitterConnectionFactory(twitterApiInfo.getApiKey(), twitterApiInfo.getApiSecret());
    }

    public OAuth1Operations getTwitterOauthOperations() {
        return connectionFactory.getOAuthOperations();
    }

    /* 트위터 로그인 경로 리턴 */
    public String getTwitterLoginUrl() {
        OAuth1Operations oauthOperations = getTwitterOauthOperations();
        OAuthToken requestToken = oauthOperations.fetchRequestToken(twitterApiInfo.getCallbackUrl(), null);
        return oauthOperations.buildAuthenticateUrl(requestToken.getValue(), OAuth1Parameters.NONE);
    }

    /* 사용자 트위터 프로필 리턴 */
    public TwitterProfile getTwitterProfile(String oauthToken, String oauthVerifier) {
        OAuthToken accessToken = getTwitterOauthOperations().exchangeForAccessToken(
                new AuthorizedRequestToken(new OAuthToken(oauthToken, ""), oauthVerifier), null);

        Twitter twitter = new TwitterTemplate(twitterApiInfo.getApiKey(),
                twitterApiInfo.getApiSecret(),
                accessToken.getValue(),
                accessToken.getSecret());

        return twitter.userOperations().getUserProfile();
    }

    /*  */

}