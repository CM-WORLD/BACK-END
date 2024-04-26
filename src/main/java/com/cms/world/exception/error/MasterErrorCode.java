package com.cms.world.exception.error;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface MasterErrorCode {
    String getExceptionMessage();

    String getMessage();

    String getMessage(Map<String, Object> params);

    String getErrorCode();

    StackTraceElement[] getCallStack();

    void setCallStack(Exception e);

    HttpStatus getStatus();

    MasterException except();

    ErrorType getType();

    void check(boolean condition) throws MasterException;
}
