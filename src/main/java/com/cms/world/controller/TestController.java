package com.cms.world.controller;

import com.cms.world.auth.MemberRepository;
import com.cms.world.domain.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@Slf4j
public class TestController {

    private final MemberRepository memberRepository;

    public Mono<Map<String, Object>> fetchData () {
        return Mono.fromCallable(() -> {
            MemberDto member = memberRepository.findById(1L).orElse(null);
            if (member != null) {
                Map<String, Object> result = new HashMap<>();
                result.put("nickName", member.getNickName());
                return result;
            }
            return Collections.emptyMap();
        });
    }

    @GetMapping("/api/test/token")
    public Mono<Map<String, Object>> test (HttpServletRequest request, HttpServletResponse response) {
            return Mono.fromCallable(() -> fetchData().block());
    }

    @GetMapping("/oauth2/callback/twitter")
    public void getTwitter() {

        TwitterConnectionFactory connectionFactory =
                new TwitterConnectionFactory("<client Key>","<client secret>");
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = oauthOperations.fetchRequestToken("https://api.twitter.com/oauth/request_token", null);

        OAuthToken accessToken = oauthOperations.exchangeForAccessToken(
                new AuthorizedRequestToken(requestToken, ""), null);
        System.out.println("Token Value:- accesstoken");
        accessToken.getSecret();
        accessToken.getValue();
        Twitter twitter = new TwitterTemplate("<client Key>",
                "<client secret>",
                accessToken.getValue(),
                accessToken.getSecret());
        TwitterProfile profile = twitter.userOperations().getUserProfile();
        System.out.println(profile.toString());

    }


//     https://rohankadam965.medium.com/how-to-implement-oauth-social-login-using-twitter-spring-boot-java-part-2-acff7f4b255a
    @GetMapping("/oauth2/authorize/normal/twitter")
    public void twitterOauthLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        TwitterConnectionFactory connectionFactory =
                new TwitterConnectionFactory("<client Key>","<client secret>");
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        oauthOperations.toString();
        OAuthToken requestToken = oauthOperations.fetchRequestToken("https://api.twitter.com/oauth/request_token", null);
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(String.valueOf(requestToken), OAuth1Parameters.NONE);
        response.sendRedirect(authorizeUrl);
    }



}
