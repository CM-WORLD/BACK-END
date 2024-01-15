package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVo {

    private Long bbsId;
    private Long parentId;
    private String content;

}
