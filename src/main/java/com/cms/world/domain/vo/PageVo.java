package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVo {

    private Integer page;

    private Integer size;

    public PageVo() {
        this.page = 1;
        this.size = 10;
    }

}
