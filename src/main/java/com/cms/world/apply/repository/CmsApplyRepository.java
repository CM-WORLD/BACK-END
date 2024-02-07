package com.cms.world.apply.repository;

import com.cms.world.apply.domain.CmsApplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CmsApplyRepository extends JpaRepository<CmsApplyDto, String> {

    Long countByStatus(String status);

    Page<CmsApplyDto> findByMemberDto_Id(Long id, Pageable pageable);

    /* 신청 내역 리스트만 조회할 때 (성능 개선) */
    @Query("SELECT c FROM CmsApplyDto c WHERE c.memberDto.id = :id")
    Page<CmsApplyDto> findListByMemberId (Long id, Pageable pageable);

    int countByStatusAndCmsDto_Id(String status, String cmsId);
}
