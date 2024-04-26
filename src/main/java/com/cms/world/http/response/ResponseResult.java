package com.cms.world.http.response;

import org.springframework.http.HttpStatus;

public class ResponseResult {
    private HttpStatus status;

    private String message;

    private Object data;

    ResponseResult(Object data) {
        this.init(HttpStatus.OK, "success", data);
    }

    ResponseResult(HttpStatus status, String message, Object data) {
        this.init(status, message, data);
    }

    ResponseResult(String message, Object data) {
        this.init(HttpStatus.OK, message, data);
    }

    public static ResponseResult data(Object data) {
        return new ResponseResult(data);
    }

    private ResponseResult init(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        return this;
    }
}
