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
    public CmsApplyDto (String user_name, String bank_owner, String cms_tp, String content) {
        this.user_name = user_name;
        this.bank_owner = bank_owner;
        this.cms_tp = cms_tp;
        this.content = content;
    }

    //필수사항
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "TP_CD")
    private String cms_tp;

    @Column(name = "CONTENT", nullable = false)
    @Length(min = 10, max = 2000)
    private String content;
    
    @Column(name = "USER_NM", nullable = false)
    private String user_name; //사용자 이름

    // TODO:: 나중에 user_id를 fk로 추가 고려

    @Column(name = "ACC_NM", nullable = false)
    private String bank_owner;

    //선택사항
    @Column(name = "PAY_AMT")
    private Double pay_amt;

    @Column(name = "DPSIT_YN")
    private String deposit_yn;

    @Column(name = "CMMNT")
    private String admin_cmmnt;
    
    @Column(name = "STATUS")
    private String status ;

    @Column(name = "END_DT")
    @CreationTimestamp
    private LocalDateTime end_date;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private LocalDateTime reg_date;

    public CmsApplyDto() {

    }

    @PrePersist
    public void doPersist () {
        this.cms_tp = GlobalCode.TYPE_SINGLE.getCode();
    }

}
