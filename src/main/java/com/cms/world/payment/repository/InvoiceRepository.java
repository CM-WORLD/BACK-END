package com.cms.world.payment.repository;

import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.payment.domain.InvoiceDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<InvoiceDto, Long> {


    /* invoice 개별 조회 */
    Optional<InvoiceDto> findById (long id);

    /* invoice 리스트 조회 (신청 ID별) */
    List<InvoiceDto> findByApplyDto_Id (String cmsApplyId);


}
