package com.cms.world.repository;

import com.cms.world.domain.dto.CmsPayDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmsPayRepository extends JpaRepository<CmsPayDto, Long> {
}
