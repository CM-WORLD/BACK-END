package com.cms.world.board.domain.reply;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVo {

    private Long id; // 댓글 id

    @NotEmpty(message = "{empty.content}")
    private String content; //댓글 내용
    private Long bbsId; //게시글 id
    private Long parentReplyId; // 부모 댓글 id
    private Long memberId; // 회원 id

}
