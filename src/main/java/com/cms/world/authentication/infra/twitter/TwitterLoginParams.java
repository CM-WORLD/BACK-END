package com.cms.world.authentication.infra.twitter;


import com.cms.world.authentication.domain.oauth.OAuthLoginParams;
import com.cms.world.authentication.domain.oauth.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@NoArgsConstructor
public class TwitterLoginParams implements OAuthLoginParams {

    private String oauthToken;

    private String oauthVerifier;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.TWITTER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("oauthToken", oauthToken);
        body.add("oauthVerifier", oauthVerifier);
        return body;
    }
}
