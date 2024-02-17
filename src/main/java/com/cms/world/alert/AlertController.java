package com.cms.world.alert;


import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/alert")
@RestController
@RequiredArgsConstructor
public class AlertController {

    private final JwtValidator jwtValidator;

    private final AlertService alertService;

    @Getter
    public static class BankTransferRequest{
        private String applyTitle;
        private String applyId;
        private Long paymentId;
    }

    @PostMapping("/bankTransfer")
    public Map<String, Object> bankTransfer(@RequestBody BankTransferRequest bankTransferRequest,
                                            HttpServletRequest request) {
        // jwt 체크
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        try {
            if (!jwtValidator.isAuthValid((int) (jwtMap.get("status")))) {
                return jwtMap;
            }
            String applyTitle = bankTransferRequest.getApplyTitle();
            String applyId = bankTransferRequest.getApplyId();
            Long paymentId = bankTransferRequest.getPaymentId();
            return CommonUtil.successResultMapWithJwt(alertService.bankTransferAlert(applyTitle, applyId, paymentId), jwtMap);
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

}
