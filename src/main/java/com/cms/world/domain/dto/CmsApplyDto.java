package com.cms.world.domain.dto;

import com.cms.world.utils.GlobalCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name="cms_aply")
@Getter
@Setter
public class CmsApplyDto {

    @Builder
    public CmsApplyDto (String userName, String bankOwner, String cmsType, String content) {
        this.userName = userName;
        this.bankOwner = bankOwner;
        this.cmsType = cmsType;
        this.content = content;
    }

    //필수사항
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "TP_CD")
    private String cmsType;

    @Column(name = "CONTENT", nullable = false)
    @Length(min = 5, max = 2000)
    private String content;
    
    @Column(name = "USER_NM", nullable = false)
    private String userName; //사용자 이름

    // TODO:: 나중에 user_id를 fk로 추가 고려

    @Column(name = "ACC_NM", nullable = false)
    private String bankOwner;

    //선택사항
    @Column(name = "PAY_AMT")
    private Double payAmt;

    @Column(name = "DPSIT_YN")
    private String depositYn;

    @Column(name = "CMMNT")
    private String adminCmmnt;
    
    @Column(name = "STATUS")
    private String status ;

    @Column(name = "END_DT")
    private LocalDateTime endDate;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private LocalDateTime regDate;

    public CmsApplyDto() {

    }

    @PrePersist
    public void doPersist () {
        this.cmsType = GlobalCode.TYPE_SINGLE.getCode();
    }

}
