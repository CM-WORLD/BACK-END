package com.cms.world.domain.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="test_")
@Getter
@Setter
public class InfReplyDto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Column(name = "CONTENT", nullable = false)
        private String content;

        @Column(name = "GRP_ID", nullable = false)
        private String groupId; // 그룹아이디

        @Column(name = "SEQ_ID", nullable = false)
        private String sequenceId; // 그룹 내 순서

        @Column(name = "LVL_ID", nullable = false)
        private String levelId; // 들여쓰기 레벨
}
