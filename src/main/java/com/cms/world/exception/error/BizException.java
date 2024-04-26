package com.cms.world.exception.error;

public class BizException extends MasterException {

    public BizException(BizErrorCode error) {
        super(error);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Exception e) {
        super(e);
    }
}
