package com.cms.world.repository;

import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsApplyImgDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CmsApplyImgRepository extends JpaRepository<CmsApplyImgDto, String> {

    List<CmsApplyImgDto> findAllByApplyDto(CmsApplyDto applyDto);
}
