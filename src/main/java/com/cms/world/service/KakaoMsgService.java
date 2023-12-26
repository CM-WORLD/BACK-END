package com.cms.world.service;


import com.cms.world.domain.social.KakaoBo;
import com.cms.world.domain.social.KakaoMsgBo;
import com.cms.world.utils.GlobalStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KakaoMsgService extends HttpCallService {

    private KakaoBo kakaoBo;

    private KakaoAuthService kakaoAuthService;

    private KakaoMsgBo msgBo;

    public KakaoMsgService(KakaoBo kakaoBo,
                           KakaoAuthService kakaoAuthService,
                           KakaoMsgBo msgBo) {
        this.kakaoBo = kakaoBo;
        this.kakaoAuthService = kakaoAuthService;
        this.msgBo = msgBo;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean sendMsg(KakaoMsgBo msgBo) {
        String accessToken = kakaoAuthService.getAuthToken();
        return process(accessToken, msgBo); //성공시 true 반환
    }

    public boolean process(String accessToken, KakaoMsgBo msgBo) {
        JSONObject linkObj = new JSONObject();
        linkObj.put("web_url", msgBo.getWebUrl());
        linkObj.put("mobile_web_url", msgBo.getMobileUrl());

        JSONObject templateObj = new JSONObject();
        templateObj.put("object_type", msgBo.getObjType());
        templateObj.put("text", msgBo.getText());
        templateObj.put("link", linkObj);
        templateObj.put("button_title", msgBo.getBtnTitle());

        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);
        header.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_object", templateObj.toString());

        HttpEntity<?> messageRequestEntity = httpClientEntity(header, parameters);

        String resultCode = "";
        ResponseEntity<String> response = httpRequest(kakaoBo.getSendMsgUrl(), HttpMethod.POST, messageRequestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        resultCode = jsonData.get("result_code").toString();

        if (resultCode.equals(String.valueOf(GlobalStatus.KAK_SEND_MSG_SUCCESS.getStatus()))) {
            logger.info(GlobalStatus.KAK_SEND_MSG_SUCCESS.getMsg());
            return true;
        }
        logger.debug(GlobalStatus.KAK_SEND_MSG_FAILED.getMsg());
        return false;
    }


}
