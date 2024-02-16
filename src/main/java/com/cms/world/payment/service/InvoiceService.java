package com.cms.world.payment.service;


import com.cms.world.payment.domain.InvoiceDto;
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

    /* 인보이스 개별 조회 */
    public InvoiceDto findDetailById (Long id) throws Exception {
        Optional<InvoiceDto> invoiceDto = repository.findById(id);

        if (!invoiceDto.isPresent()) {
            throw new Exception("해당 ID의 인보이스가 존재하지 않음");
        }
        return invoiceDto.get();
    }

    /* 인보이스 리스트 조회 */
    public List<InvoiceDto> findByCmsApplyId(String cmsApplyId) {
        return repository.findByApplyDto_Id(cmsApplyId);
    }


}
