package com.cms.world.exception.error;

public class MasterException extends Exception{

    public MasterErrorCode errorCode;

    public MasterException() {
    }

    public MasterException(String message) {
        super(message);
    }

    public MasterException(Exception e) {
        super(e);
    }

    public MasterException(MasterErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MasterErrorCode getErrorCode() {
        return this.errorCode;
    }
}
