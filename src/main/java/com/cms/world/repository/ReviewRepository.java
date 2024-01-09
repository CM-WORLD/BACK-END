package com.cms.world.repository;

import com.cms.world.domain.dto.ReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewDto, Long> {
}
