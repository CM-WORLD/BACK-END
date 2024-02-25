package com.cms.world.trash;


import com.cms.world.common.util.StringUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Service
public class HttpCallService {
    //카카오 메세지 보내기하면서 추가됨.
    /*https://github.com/asd9211/kakao_api_demo/blob/master/src/main/java/com/example/demo/base/service/HttpCallService.java*/

    protected static final String APP_TYPE_URL_ENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    protected static final String APP_TYPE_JSON = "application/json;charset=UTF-8";

    public HttpEntity<?> httpClientEntity(HttpHeaders header, Object params) {
        HttpHeaders requestHeaders = header;

        if(StringUtil.isEmpty(params.toString())) {
            return new HttpEntity<>(requestHeaders);
        } else {
            return new HttpEntity<>(params, requestHeaders);
        }
    }

     /* http 요청 메서드 */
    public ResponseEntity<String> httpRequest(String url, HttpMethod method, HttpEntity<?> entity) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, entity, String.class);
    }


    /* URI를 파람으로 받는다. */
    public ResponseEntity<String> httpRequest(URI url, HttpMethod method, HttpEntity<?> entity) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, entity, String.class);
    }

}
