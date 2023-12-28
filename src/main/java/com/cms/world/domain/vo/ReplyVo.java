package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVo {

    private Long boardId;
    private Long parentId;
    private String content;
    private String writer;
    private String regDate;

}
