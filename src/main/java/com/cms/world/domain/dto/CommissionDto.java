package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "commission")
@Getter
@Setter
public class CommissionDto {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "PPOFILE_IMG", nullable = false)
    private String profileImg;

    @Column(name = "STATUS", nullable = false) // 신청 닫힘/열림
    private String status;

    @Column(name = "DEL_YN", nullable = false)
    private String delYn;

    @Column(name = "RGTR_DT", nullable = false)
    @CreationTimestamp
    private String regDate;

    @Column(name = "UPT_DT")
    @UpdateTimestamp
    private String uptDate;

    @Transient
    private Long prsCnt;

    @Transient
    private Long rsvCnt;

    @PrePersist
    public void doBefore() {
        if (StringUtil.isEmpty(delYn)) {
            this.delYn = "N";
        }
        if (StringUtil.isEmpty(status)) {
            this.status = GlobalCode.CMS_OPENED.getCode();
        }
    }
}
