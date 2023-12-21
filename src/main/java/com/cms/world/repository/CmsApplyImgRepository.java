package com.cms.world.repository;

import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsApplyImgDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CmsApplyImgRepository extends JpaRepository<CmsApplyImgDto, String> {
}
