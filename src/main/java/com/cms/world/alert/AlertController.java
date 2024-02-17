package com.cms.world.alert;


import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/alert")
@RestController
@RequiredArgsConstructor
public class AlertController {

    private final JwtValidator jwtValidator;

    private final AlertService alertService;

    @PostMapping("/bankTransfer")
    public Map<String, Object> bankTransfer(@RequestBody String applyTitle,
                                            @RequestBody String applyId,
                                            @RequestBody String paymentId,
                                            HttpServletRequest request) {
        // jwt 체크
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        try {
            if (!jwtValidator.isAuthValid((int) (jwtMap.get("status")))) {
                return jwtMap;
            }
            return CommonUtil.successResultMapWithJwt(alertService.bankTransferAlert(applyTitle, applyId, paymentId), jwtMap);
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

}
