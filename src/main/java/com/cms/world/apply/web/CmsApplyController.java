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

    /* 커미션 신청 상세 TODO:: 리팩토링 */
    @GetMapping("/detail")
    public Map<String, Object> detail (@RequestParam(name = "cmsApplyId") String cmsApplyId) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
            map.put("data", service.detail(cmsApplyId));
            map.put("applyImgList", service.imgListByStatus(cmsApplyId, GlobalCode.APPLIED_IMG.getCode()));
            map.put("completeImgList", service.imgListByStatus(cmsApplyId, GlobalCode.COMPLETE_IMG.getCode()));

        } catch (Exception e) {
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

}
