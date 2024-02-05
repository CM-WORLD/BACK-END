package com.cms.world.authentication.infra.naver;

import com.cms.world.authentication.domain.oauth.OAuthLoginParams;
import com.cms.world.authentication.domain.oauth.OAuthProvider;
import com.cms.world.utils.GlobalCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private String code;
    private String state;

    @Override
    public String oAuthProvider() {
        return GlobalCode.OAUTH_NAVER.getCode();
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("state", state);
        return body;
    }
}