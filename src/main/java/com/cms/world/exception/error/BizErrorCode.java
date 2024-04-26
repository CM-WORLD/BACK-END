package com.cms.world.exception.error;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public enum BizErrorCode implements MasterErrorCode {

    INTERNAL_SERVER_ERROR("500", "서버 에러입니다. 서버 팀에 연락주세요!");

    String errorCode;
    String message;
    String exceptMessage;
    ErrorType type;
    StackTraceElement[] callStack;

    private BizErrorCode(String errorCode, String message) {
        this.type = ErrorType.Biz;
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getExceptionMessage() {
        return null;
    }

    @Override
    public String getMessage(Map<String, Object> params) {
        return null;
    }

    @Override
    public void setCallStack(Exception e) {

    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public MasterException except() {
        return null;
    }

    public void check(boolean condition) throws BizException {
        if (condition) {
            throw new BizException(this);
        }
    }
}
