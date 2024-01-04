package com.cms.world.controller;


import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.social.TelegramChat;
import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.service.CmsApplyService;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apply")
public class CmsApplyController {

    private CmsApplyService service;

    public CmsApplyController(CmsApplyService service) {
        this.service = service;
    }

    @Autowired
    TelegramChat telegramChat;

    /* 커미션 신청 */
    @PostMapping("/form")
    public Map<Integer, String> submit(CmsApplyVo vo) {
        Map<Integer, String> map = new HashMap<>();
        try {
            if (service.insert(vo) == GlobalStatus.EXECUTE_SUCCESS.getStatus()) {
                map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
                //TODO:: telegram alert 전송
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

    /* 커미션 신청 리스트 */
    @GetMapping("/list")
    public Map<String, List<CmsApplyDto>> list() {
        Map<String, List<CmsApplyDto>> listMap = new HashMap<>();
        listMap.put("list", service.list());
        return listMap;
    }

    /* 커미션 상태 변경 */
    @PutMapping("/{id}")
    public Map<Integer, String> updateStatus(@PathVariable String id, String status) {
        Map<Integer, String> map = new HashMap<>();
        if (service.updateStatus(id, status) == GlobalStatus.EXECUTE_SUCCESS.getStatus()) {
            map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
        } else {
            map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

    /* 커미션 진행중 count 조회 */
    @GetMapping("/cnt/processing")
    public Map<String, Object> cntByProcessing() {
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", service.cntByStatus(GlobalCode.CMS_PROCESS.getCode()));
        return map;
    }

    /* 커미션 예약중 count 조회 */
    @GetMapping("/cnt/reserved")
    public Map<String, Object> cntByRsv() {
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", service.cntByStatus(GlobalCode.CMS_RESERVE.getCode()));
        return map;
    }


}
