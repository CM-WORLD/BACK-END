package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="review")
@Entity
@Getter
@Setter
public class ReviewDto {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CMS_APLY_ID", referencedColumnName = "ID")
    private CommissionDto cmsDto;

    @Column(name="CONTENT")
    private String content;

    @Column(name="WRT_ID")
    private String writer;

    @Column(name="DSPY_YN")
    private String displayYn;



}
