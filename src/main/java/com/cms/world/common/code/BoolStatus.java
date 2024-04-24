package com.cms.world.common.code;

public enum BoolStatus {

    Y("1", true),
    N("0", false);

    private String flag;
    private boolean value;

    BoolStatus(String flag, boolean value) {
        this.flag = flag;
        this.value = value;
    }

    public String getFlag() {
        return flag;
    }

    public boolean getVal() {
        return value;
    }
}
