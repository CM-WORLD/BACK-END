package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="user")
@Getter
@Setter
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USER_NM", nullable = false)
    private String username;

    @Column(name = "STATUS")
    private String status;

    @Column(name ="LAST_LOGIN_TM")
    private String lastLoginTime;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;

    @PrePersist
    public void doPersist() {
        if (StringUtil.isEmpty(this.status)) {
            this.setStatus(GlobalCode.USER_ACTIVE.getCode());
        }
    }



}
