package com.cms.world.repository;

import com.cms.world.domain.dto.ReviewDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReviewRepository extends JpaRepository<ReviewDto, Long> {

    @Query(value = "SELECT\n" +
            "    CVW.*,\n" +
            "    CVW.RGTR_DT AS regDate\n" +
            "FROM cms_rvw CVW\n" +
            "WHERE (CVW.MEM_ID = :memberId OR :memberId IS NULL) AND (CVW.DSPY_YN = 'Y' OR :memberId IS NULL)",
            nativeQuery = true)
    Page<ReviewDto> findListWithCond(@Param("memberId") Long memberId, Pageable pageable);


}
