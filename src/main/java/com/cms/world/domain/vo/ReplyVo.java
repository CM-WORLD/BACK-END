package com.cms.world.domain.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVo {

    private Long id; //replyId
    private Long bbsId;
    private Long parentId; // 부모 대댓글 id
    private String parentPath; // 부모 대댓글 경로
    private String content;

}
