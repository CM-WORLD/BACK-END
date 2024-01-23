package com.cms.world.repository;

import com.cms.world.domain.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewDto, Long> {

    Page<ReviewDto> findAll (Pageable pageable);

    Page<ReviewDto> findByMemberId(Long memberId, Pageable pageable);

}
