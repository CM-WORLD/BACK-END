package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVo {

    private String title;
    private String content;
    private String type;
    private String writer;
}
