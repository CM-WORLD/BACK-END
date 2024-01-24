package com.cms.world.repository;

import com.cms.world.domain.dto.ReviewDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReviewRepository extends JpaRepository<ReviewDto, Long> {

    Page<ReviewDto> findLitByMemberId(Long memberId, Pageable pageable);

    Page<ReviewDto> findAll (Pageable pageable);


}
