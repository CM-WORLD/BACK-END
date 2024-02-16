package com.cms.world.payment.web;


import com.cms.world.payment.service.InvoiceService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    private final JwtValidator jwtValidator;

    /* 신청 id별 인보이스 리스트 조회 */
    @GetMapping("/list/{cmsApplyId}")
    public Map<String, Object> listByCmsApplyId(@PathVariable("cmsApplyId") String cmsApplyId,
                                                HttpServletRequest request) {
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        try {
            if (!jwtValidator.isAuthValid((int) (jwtMap.get("status")))) {
                return jwtMap;
            }
            return CommonUtil.successResultMapWithJwt(invoiceService.findByCmsApplyId(cmsApplyId), jwtMap);
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

//    /* 인보이스 id별 상세 조회 */
//    @GetMapping("/invoice")
//    public Map<String, Object> findDetailById(Long id) {
//        try {
//            return CommonUtil.renderResultByMap(invoiceService.findDetailById(id));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
//        }
//    }
}
