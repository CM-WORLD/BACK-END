package com.cms.world.payment.repository;

import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.payment.domain.InvoiceDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<InvoiceDto, Long> {


    /* invoice 개별 조회 */
    Optional<InvoiceDto> findById (String id);


}
