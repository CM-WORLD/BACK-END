package com.cms.world.payment;

import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.payment.domain.InvoiceDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmsPayRepository extends JpaRepository<InvoiceDto, Long> {

    InvoiceDto findByApplyDto (CmsApplyDto dto);
}
