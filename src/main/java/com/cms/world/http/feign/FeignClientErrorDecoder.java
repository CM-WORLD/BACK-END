package com.cms.world.http.feign;


import feign.codec.ErrorDecoder;
import feign.Response;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FeignClientErrorDecoder implements ErrorDecoder {

    public Exception decode(String methodKey, Response response) {
        // feignclienterror시 원하는 응답 형식으로 format한다.
        response.request().url();

        /**
         * error format을 지정한다.
         */

        ErrorDecoder decoder = new ErrorDecoder.Default();
        return decoder.decode(methodKey, response);
    }
}
