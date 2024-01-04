package com.cms.world.repository;

import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsPayDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmsPayRepository extends JpaRepository<CmsPayDto, Long> {

    CmsPayDto findByApplyDto (CmsApplyDto dto);
}
