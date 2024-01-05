package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="member")
@Getter
@Setter
public class MemberDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "NICK_NM", nullable = false, unique = true)
    private String nickName;

    @Column(name = "STATUS")
    private String status;

    @Column(name ="LOG_IN_DT")
    private String lastLoginTime;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;

    @PrePersist
    public void doPersist () {
        if (StringUtil.isEmpty(this.status)) {
            this.setStatus(GlobalCode.USER_ACTIVE.getCode());
        }
    }
}
