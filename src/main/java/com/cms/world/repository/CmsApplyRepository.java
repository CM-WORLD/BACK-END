package com.cms.world.repository;

import com.cms.world.domain.dto.CmsApplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CmsApplyRepository extends JpaRepository<CmsApplyDto, String> {

    Long countByStatus(String status);

    Page<CmsApplyDto> findByMemberDto_Id(Long id, Pageable pageable);
}
