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

    @Column(name="TITLE")
    private String title;

    @Column(name="WRT_ID")
    private String writer;
}
