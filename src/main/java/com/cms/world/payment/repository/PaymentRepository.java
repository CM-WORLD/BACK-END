package com.cms.world.payment.repository;

import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.payment.domain.PaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentDto, Long> {

    /* 결제 요청서들 조회 */

    /* 결제 요청서 조회 */
    List<PaymentDto> findByApplyDto_Id(String applyId);

}
