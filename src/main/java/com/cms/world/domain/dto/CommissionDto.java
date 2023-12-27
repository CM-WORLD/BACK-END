package com.cms.world.domain.dto;


import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Update;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "cms_list")
@Getter
@Setter
public class CommissionDto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PPOFILE_IMG")
    private String profileImg;
    
    @Column(name = "STATUS") // 신청 닫힘/열림
    private String status;

    @Column(name = "DEL_YN")
    private String delYn;

    @Column(name = "RGTR_DT")
    @CreationTimestamp
    private String regDate;

    @Column(name = "UPT_DT")
    @UpdateTimestamp
    private String uptDate;

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
