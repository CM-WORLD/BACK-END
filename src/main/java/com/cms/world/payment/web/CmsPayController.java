package com.cms.world.payment.web;


import com.cms.world.payment.CmsPayVo;
import com.cms.world.payment.CmsPayService;
import com.cms.world.payment.service.InvoiceService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class CmsPayController {

    private final CmsPayService service;

    private final InvoiceService invoiceService;

    @PostMapping("/form")
    public Map<String, Object> form(CmsPayVo vo) {
        Map<String, Object> map = new HashMap<>();
        try {
            service.form(vo);
            map.put("status", GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
            return map;
        } catch (Exception e) {
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
            return map;
        }
    }

//    /**
//     * 신청 ID별 결제요청서 조회
//     */
//    @GetMapping("/")
//    public Map<String, Object> getPayList(String applyId) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            map.put("status", GlobalStatus.SUCCESS.getStatus());
//            map.put("msg", GlobalStatus.SUCCESS.getMsg());
////            map.put("data", service.getPayList(applyId));
//            return map;
//        } catch (Exception e) {
//            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
//        }
//    }


    /* 인보이스 id별 상세 조회 */
    @GetMapping("/")
    public Map<String, Object> findDetailById(String id) {
        try {
            return CommonUtil.renderResultByMap(invoiceService.findDetailById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

}
