package com.cms.world.domain.vo;


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
    private Long groupId;
    private Long levelId;
    private Long sequenceId;

    public ReplyVo() {
        this.levelId = 0L;
        this.sequenceId = 0L;
    }

}
