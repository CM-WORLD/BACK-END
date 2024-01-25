package com.cms.world.domain.dto;


import com.cms.world.utils.DateUtil;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name="bbs_reply")
@Getter
@Setter
public class ReplyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "BBS_ID", referencedColumnName = "ID")
    private BoardDto boardDto;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "PRNT_ID", referencedColumnName = "ID")
//    private ReplyDto parent;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
//    private List<ReplyDto> children;

    @Column(name = "GRP_ID", nullable = false)
    private String groupId; // 그룹아이디

    @Column(name = "LVL_ID", nullable = false)
    private String levelId; // 들여쓰기 레벨

    @Column(name = "SEQ_ID", nullable = false)
    private String sequenceId; // 그룹 내 순서
    
    @Column(name = "MEM_ID", nullable = false)
    private Long memberId;

    @Transient
    private boolean isMyReply;

    @Transient
    private String nickName;

    @Column(name = "RGTR_DT")
    private String regDate;

    @Column(name = "UPT_DT")
    private String uptDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
    }
}
