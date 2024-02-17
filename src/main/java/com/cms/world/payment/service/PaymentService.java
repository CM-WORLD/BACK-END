package com.cms.world.payment.service;


import com.cms.world.apply.repository.CmsApplyRepository;
import com.cms.world.payment.domain.PaymentVo;
import com.cms.world.payment.repository.PaymentRepository;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    @Autowired
    private CmsApplyRepository cmsApplyRepository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public int form (PaymentVo vo) throws Exception {
//        Optional<CmsApplyDto> applyDto = cmsApplyRepository.findById(vo.getApplyId());
//
//        if (!applyDto.isPresent()) throw new Exception("applyDto for payment not found");
//
//        InvoiceDto dto = new InvoiceDto();
//        dto.setApplyDto(applyDto.get());
//        dto.setPayAmt(vo.getPayAmt());
//        dto.setComment(vo.getComment());
//
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(vo.getEndDate().toInstant(), ZoneId.systemDefault());
//        dto.setEndDate(localDateTime);
//
//        if(repository.save(dto) == null) throw new Exception("payRepository.save returned null");
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }
}
