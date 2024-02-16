package com.cms.world.payment.service;


import com.cms.world.payment.domain.InvoiceDtlDto;
import com.cms.world.payment.domain.InvoiceDto;
import com.cms.world.payment.repository.InvoiceDtlRepository;
import com.cms.world.payment.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;

    private final InvoiceDtlRepository invoiceDtlRepository;

    /* 인보이스 상세와 인보이스 상세 항목 리스트 전부 조회 */
    public Map<String, Object> findDetailById (Long id) throws Exception {
        Map<String, Object> map = new HashMap<>();

        Optional<InvoiceDto> invoiceDto = repository.findById(id);

        if (!invoiceDto.isPresent()) {
            throw new Exception("해당 ID의 인보이스가 존재하지 않음");
        } else map.put("invoice", invoiceDto.get());

        Long invoiceId = invoiceDto.get().getId();

        Optional<List<InvoiceDtlDto>> invoideDtlList = invoiceDtlRepository.findByInvoiceDto_id(invoiceId);

        if (invoideDtlList.isPresent()) {
            throw new Exception("해당 ID의 인보이스 상세 항목이 존재하지 않음");
        } else map.put("invoiceDtl", invoiceDtlRepository.findById(invoiceId).get());

        return map;
    }

    public List<InvoiceDto> findByCmsApplyId(String cmsApplyId) {
        return repository.findByApplyDto_Id(cmsApplyId);
    }


}
