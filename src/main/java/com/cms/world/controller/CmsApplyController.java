package com.cms.world.controller;

import com.cms.world.authentication.domain.AuthTokensGenerator;

import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.service.CmsApplyService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor
public class CmsApplyController {

    private final CmsApplyService service;

    private final AuthTokensGenerator authTokensGenerator;

    /* 커미션 신청 */
    @PostMapping("/apply/form")
    public Map<String, Object> submit(HttpServletRequest request, CmsApplyVo vo) {
        Map<String, Object> map = new HashMap<>();
        try {
            vo.setUserId(authTokensGenerator.extractMemberIdFromReq(request)); // req로부터 id 추출);
            String cmsId = service.insert(vo);
            if (!StringUtil.isEmpty(service.insert(vo))) {
                map.put("status", String.valueOf(GlobalStatus.SUCCESS.getStatus()));
                map.put("msg", String.valueOf(GlobalStatus.SUCCESS.getMsg()));
                map.put("cmsId", cmsId);

                //TODO:: telegram alert 전송
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", String.valueOf(GlobalStatus.INTERNAL_SERVER_ERR.getStatus()));
            map.put("msg", String.valueOf(GlobalStatus.INTERNAL_SERVER_ERR.getMsg()));
        }
        return map;
    }

    /* 커미션 전체 신청 리스트 */
    @GetMapping("/auth/apply/list/all")
    public Map<String, List<CmsApplyDto>> list() {
        Map<String, List<CmsApplyDto>> listMap = new HashMap<>();
        listMap.put("list", service.list());
        return listMap;
    }

    /* 사용자별 신청 내역 */
    @GetMapping("/apply/history")
    public Map<String, Object> applyHistoryByMemberId (HttpServletRequest request,
                                                     @RequestParam(name= "page", defaultValue = "0") Integer page,
                                                     @RequestParam(name = "size", defaultValue = "10") Integer size)  {
        Long id = authTokensGenerator.extractMemberIdFromReq(request);
        return CommonUtil.renderResultByMap(service.listByMemberID(id, page, size));
    }

    /* 커미션 신청 상세 */
    @GetMapping("/apply/detail")
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

    /* 커미션 상태 변경 */
    @PutMapping("/auth/apply/{id}")
    public Map<Integer, String> updateStatus(@PathVariable String id, String status) {
        Map<Integer, String> map = new HashMap<>();
        if (service.updateStatus(id, status) == GlobalStatus.EXECUTE_SUCCESS.getStatus()) {
            map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
        } else {
            map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

    /* 커미션 타입 변경 (관리자) */
    @PutMapping("/auth/apply/type/{id}/{cmsType}")
    public Map<String, Object> updateTp (@PathVariable String id, @PathVariable String cmsType) {
        Map<String, Object> map = new HashMap<>();
        try {
            service.updateTp(id, cmsType);
            map.put("status", GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
        } catch (Exception e) {
            map.put("status", GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

}
