package com.cms.world.apply.repository;

import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.apply.domain.ApplyImgDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CmsApplyImgRepository extends JpaRepository<ApplyImgDto, String> {

    List<ApplyImgDto> findAllByApplyDto(ApplyDto applyDto);

    List<ApplyImgDto> findByApplyDto_IdAndStatus (String applyId, String status);
}
