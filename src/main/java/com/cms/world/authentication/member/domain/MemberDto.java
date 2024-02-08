package com.cms.world.authentication.member.domain;


import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Table(name="member")
@Entity
@Getter
@Setter
public class MemberDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UID", nullable = false)
    private String uid;

    // 이메일보다 phone이 더 필요해 보이는데....  // 일단 로그인 시 이메일을 받지 않도록 한다.

    @Column(name = "NICK_NM", nullable = false)
    private String nickName;

    @Schema(description = "프로필 이미지 url", example = "http://localhost:8080/img/profile/1.jpg")
    @Column(name = "PROF_IMG", nullable = false)
    private String profileImg;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "LOGIN_TP", nullable = false)
    private String loginType;

    @Column(name ="LOG_IN_DT")
    private String lastLoginTime;

    // 네이버 로그아웃 용도로 네이버 accessToken 저장
    @Column(name ="ATK_VAL")
    private String accessToken;

    @Column(name ="RTK_VAL")
    private String refreshToken;

    @Column(name = "RGTR_DT")
    private String regDate;

    @PrePersist
    public void doPersist () {
        if (StringUtil.isEmpty(this.status)) {
            this.setStatus(GlobalCode.USER_ACTIVE.getCode());
        }
        this.setRegDate(DateUtil.currentDateTime());
    }
}
