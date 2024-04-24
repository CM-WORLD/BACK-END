package com.cms.world.review;

import com.cms.world.review.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<ReviewDto, Long> {

    Page<ReviewDto> findLitByMemberId(Long memberId, Pageable pageable);

    Page<ReviewDto> findAll (Pageable pageable);


}
