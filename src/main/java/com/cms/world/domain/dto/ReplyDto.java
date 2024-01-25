package com.cms.world.domain.dto;


import com.cms.world.utils.DateUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


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

    @Column(name = "GRP_ID")
    @ColumnDefault("0")
    private Long groupId; // 그룹아이디

    @Column(name = "LVL_ID")
    private Long levelId; // 들여쓰기 레벨

    @Column(name = "SEQ_ID")
    @ColumnDefault("0")
    private Long sequenceId; // 그룹 내 순서
    
    @Column(name = "MEM_ID", nullable = false)
    private Long memberId;

    @Column(name = "PRNT_ID")
    private Long parentId;

    @Transient
    private boolean isMyReply;

    @Transient
    private String nickName;

    @Transient
    private boolean hasChildren;

    @Column(name = "RGTR_DT")
    private String regDate;

    @Column(name = "UPT_DT")
    private String uptDate;

    @PrePersist
    public void doPersist () {
        this.setRegDate(DateUtil.currentDateTime());
        if(this.getGroupId() == null) this.setGroupId(0L);
        if(this.getSequenceId() == null) this.setSequenceId(0L);
        if(this.getLevelId() == null) this.setLevelId(0L);
    }
}
