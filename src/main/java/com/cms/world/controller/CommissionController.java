package com.cms.world.controller;

import com.cms.world.domain.dto.CommissionDto;
import com.cms.world.domain.vo.CommissionVo;
import com.cms.world.service.CmsApplyService;
import com.cms.world.service.CommissionService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cms")
@RequiredArgsConstructor
public class CommissionController {

    private final CommissionService service;

    private final CmsApplyService cmsApplyService;


    @PostMapping("/form")
    public Map<Integer, String> submit(CommissionVo vo) {
        Map<Integer, String> map = new HashMap<>();
        try {
            service.insert(vo);
            map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

    @GetMapping("/list")
    public Map<String, Object> list () {
        Map<String, Object> map = new HashMap<>();
        try {
            List<CommissionDto> list = service.list("N");
            for(CommissionDto dto : list) {

                dto.setPrsCnt(cmsApplyService.applyCntByStatus( GlobalCode.CMS_PROCESS.getCode(), dto.getId()));
                dto.setRsvCnt(cmsApplyService.applyCntByStatus( GlobalCode.CMS_RESERVE.getCode(), dto.getId()));

                System.out.println("dto.getPrsCnt() = " + dto.getPrsCnt());
                System.out.println("dto.getPrsCnt() = " + dto.getRsvCnt());
            }
            map.put("status" , GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
            map.put("data", list);
        } catch (Exception e) {
            map.put("status" , GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

    /* 커미션 신청 id 존재 여부 확인 */
    @GetMapping("/check/id")
    public Map<String, Object> isIdExisting (String id) {
        Map<String, Object> map = new HashMap<>();

        if (service.getById(id).isPresent()) {
            map.put("status", GlobalStatus.SUCCESS.getStatus());
            map.put("msg", "존재하는 커미션 아이디");
        } else {
            map.put("status", GlobalStatus.NOT_FOUND.getStatus());
            map.put("msg", GlobalStatus.NOT_FOUND.getMsg());
        }
        return map;
    }

    @PutMapping("/close/all")
    public Map<String, Object> closeAll() {
        Map<String, Object> resultMap = CommonUtil.resultMap(service.toggleAllStatus(GlobalCode.CMS_CLOSED.getCode()));
        return resultMap;
    }

    @PutMapping("/open/all")
    public Map<String, Object> openAll() {
        Map<String, Object> resultMap = CommonUtil.resultMap(service.toggleAllStatus(GlobalCode.CMS_OPENED.getCode()));
        return resultMap;
    }

    @PutMapping("/toggle")
    public Map<String, Object> toggleStatus (@RequestBody Map<String, String> data) {
        Map<String, Object> resultMap = CommonUtil.resultMap(service.toggleStatus(data.get("id")));
        return resultMap;
    }
}
