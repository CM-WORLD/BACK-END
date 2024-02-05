package com.cms.world.controller;


import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.authentication.domain.AuthTokensGenerator;
import com.cms.world.service.ReplyService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.validator.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService service;

    private final AuthTokensGenerator authTokensGenerator;

    private final JwtValidator jwtValidator;

    /* 게시글당 댓글 조회 */
    @GetMapping("/{bbsId}")
    public Map<String, Object> list (HttpServletRequest request, @PathVariable("bbsId") Long bbsId) {
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        try {
            if (!jwtValidator.isAuthValid((int) (jwtMap.get("status")))) {
                return jwtMap;
            }

            Long memberId = authTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출
            return CommonUtil.successResultMap(service.listByBbsId(bbsId, memberId));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

    /* 루트/대댓글 추가 */
    @PostMapping("/")
    public Map<String,Object> insert (HttpServletRequest request, @Validated ReplyVo vo, BindingResult bindingResult) throws Exception {
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        try {
            if (!jwtValidator.isAuthValid((int) (jwtMap.get("status")))) {
                return jwtMap;
            }
            if (bindingResult.hasErrors()) {
                return CommonUtil.failResultMapWithJwt(GlobalStatus.BAD_REQUEST.getStatus(), bindingResult.getFieldError().getDefaultMessage(), jwtMap);
            }
            Long memberId = authTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출
            vo.setMemberId(memberId);

            return CommonUtil.resultMap(service.insert(vo));
        } catch (Exception e) {
            return CommonUtil.failResultMapWithJwt(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage(), jwtMap);
        }
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> delete (@PathVariable("id") Long id) throws Exception {
        System.out.println("id = " + id);
        return CommonUtil.resultMap(service.delete(id));
    }

    /* 댓글 수정 */
    @PutMapping("/") //test ok
    public Map<String, Object> update (HttpServletRequest request, @Validated ReplyVo vo, BindingResult bindingResult) {
        Map<String, Object> jwtMap = jwtValidator.validate(request);
        try {
            if (!jwtValidator.isAuthValid((int) (jwtMap.get("status")))) {
                return jwtMap;
            }
            if (bindingResult.hasErrors()) {
                return CommonUtil.failResultMapWithJwt(GlobalStatus.BAD_REQUEST.getStatus(), bindingResult.getFieldError().getDefaultMessage(), jwtMap);
            }
            return CommonUtil.resultMap(service.update(vo));
        } catch (Exception e) {
            return CommonUtil.failResultMapWithJwt(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage(), jwtMap);
        }
    }
}
