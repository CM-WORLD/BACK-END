package com.cms.world.apply.repository;

import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.apply.domain.CmsApplyImgDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CmsApplyImgRepository extends JpaRepository<CmsApplyImgDto, String> {

    List<CmsApplyImgDto> findAllByApplyDto(CmsApplyDto applyDto);

    List<CmsApplyImgDto> findByStatus (String status);
}
