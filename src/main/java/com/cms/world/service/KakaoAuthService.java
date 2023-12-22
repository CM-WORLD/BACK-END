package com.cms.world.service;


import com.cms.world.domain.bo.KakaoBo;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KakaoAuthService extends HttpCallService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private KakaoBo kakaoBo;

    private String authToken;

    public KakaoAuthService(KakaoBo kakaoBo) {
        this.kakaoBo = kakaoBo;
    }

    public String getAuthToken() {
        return authToken;
    }

    // redirectUrl을 통해서 얻은 코드로 인증 토큰을 authToken에 저장한다.
    public int saveToken(String code) {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refreshToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("code", code);
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", kakaoBo.getClientId());
        parameters.add("redirect_url", kakaoBo.getRedirectUrl());
        parameters.add("client_secret", kakaoBo.getClientSecret());

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);
        ResponseEntity<String> response = httpRequest(kakaoBo.getAuthUrl(), HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        accessToken = jsonData.get("access_token").toString();
        refreshToken = jsonData.get("refresh_token").toString();
        if (StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(refreshToken)) {
            logger.debug(GlobalStatus.KAK_CRT_TOKEN_FAILED.getMsg());
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        } else {
            authToken = accessToken;
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        }
    }

    public String getLoginUrl() { // 카카오 로그인 페이지로 가는 경로
        return "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + kakaoBo.getClientId() +
                "&redirect_uri=" + kakaoBo.getRedirectUrl() +
                "&response_type=code" +
                "&scope=talk_message";
    }


}
