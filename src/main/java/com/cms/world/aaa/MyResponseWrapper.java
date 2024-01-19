package com.cms.world.aaa;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyResponseWrapper {
    private Object data;
    private String newAccessToken;

    public MyResponseWrapper(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
