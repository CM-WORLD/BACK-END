package com.cms.world.payment.repository;

import com.cms.world.payment.domain.InvoiceDtlDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceDtlRepository extends JpaRepository<InvoiceDtlDto, Long> {

    /* 인보이스 개별 항목 리스트 조회  */
    Optional<List<InvoiceDtlDto>> findById (String id);
}
