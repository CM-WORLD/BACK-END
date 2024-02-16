package com.cms.world.apply.web;

import com.cms.world.authentication.domain.AuthTokensGenerator;

import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.apply.domain.CmsApplyVo;
import com.cms.world.apply.service.CmsApplyService;
import com.cms.world.common.DtoMapper;
import com.cms.world.common.Response;
import com.cms.world.domain.vo.BoardVo;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class CmsApplyController {

    private final CmsApplyService service;

    private final AuthTokensGenerator authTokensGenerator;

    private final JwtValidator jwtValidator;

    /* 커미션 신청 */
    @PostMapping("/form")
    public Map<String, Object> submit(HttpServletRequest request,
                                      @Validated(CmsApplyVo.CmsApplyVoSequence.class) CmsApplyVo vo,
                                      BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return CommonUtil.failResultMap(GlobalStatus.BAD_REQUEST.getStatus(), bindingResult.getFieldError().getDefaultMessage());
            }
            vo.setUserId(authTokensGenerator.extractMemberIdFromReq(request)); // 사용자 아이디 삽입
            return CommonUtil.successResultMap(service.insert(vo));

        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

    /* 사용자별 신청 내역 */
    @GetMapping("/member/history")
    public Map<String, Object> applyHistoryByMemberId (HttpServletRequest request,
                                                     @RequestParam(name= "page", defaultValue = "0") Integer page,
                                                     @RequestParam(name = "size", defaultValue = "10") Integer size)  {
        Long id = authTokensGenerator.extractMemberIdFromReq(request);
        return CommonUtil.renderResultByMap(service.listByMemberID(id, page, size));
    }

    /* 커미션 신청 상세 */
    @GetMapping("/detail")
    public Map<String, Object> detail (@RequestParam(name = "cmsApplyId") String cmsApplyId,
                                       HttpServletRequest request) {
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        try {
            if (!jwtValidator.isAuthValid((int) (jwtMap.get("status")))) {
                return jwtMap;
            }
            return CommonUtil.successResultMapWithJwt(service.detail(cmsApplyId), jwtMap);
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

}
