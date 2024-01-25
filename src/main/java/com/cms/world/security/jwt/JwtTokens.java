package com.cms.world.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokens {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static JwtTokens of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new JwtTokens(accessToken, refreshToken, grantType, expiresIn);
    }
}