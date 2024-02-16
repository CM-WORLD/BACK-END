package com.cms.world.payment.service;


import com.cms.world.payment.repository.InvoiceDtlRepository;
import com.cms.world.payment.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;

    private final InvoiceDtlRepository invoiceDtlRepository;

    /* 인보이스 상세와 인보이스 상세 항목 리스트 전부 조회 */
    public Map<String, Object> findDetailById (String id) throws Exception {
        Map<String, Object> map = new HashMap<>();

        if (!repository.findById(id).isPresent()) {
            throw new Exception("해당 ID의 인보이스가 존재하지 않음");
        } else map.put("invoice", repository.findById(id).get());

        if (!invoiceDtlRepository.findById(id).isPresent()) {
            throw new Exception("해당 ID의 인보이스 상세 항목이 존재하지 않음");
        } else map.put("invoiceDtl", invoiceDtlRepository.findById(id).get());

        return map;
    }


}
