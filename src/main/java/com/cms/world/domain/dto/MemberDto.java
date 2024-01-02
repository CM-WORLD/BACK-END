package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="user")
@Getter
@Setter
public class MemberDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "NICK_NM", nullable = false)
    private String nickName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;

    @Column(name = "LOG_IN_DT")
    @UpdateTimestamp
    private String lastLoginTime;

    @PrePersist
    public void doPersist () {
        if(StringUtil.isEmpty(status)) {
            this.setStatus(GlobalCode.USER_ACTIVE.getCode());
        }
    }

}
