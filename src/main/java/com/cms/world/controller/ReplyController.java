package com.cms.world.controller;


import com.cms.world.domain.vo.ReplyVo;
import com.cms.world.security.jwt.JwtTokensGenerator;
import com.cms.world.service.ReplyService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService service;

    private final JwtTokensGenerator jwtTokensGenerator;

    /* 게시글당 댓글 조회 */
    @GetMapping("/{bbsId}")
    public Map<String, Object> list (HttpServletRequest request, @PathVariable("bbsId") Long bbsId) {
        try {
            Long memberId = jwtTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출
            return CommonUtil.successResultMap(service.listByBbsId(bbsId, memberId));
        } catch (Exception e) {
            return CommonUtil.failResultMap(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/")
    public Map<String,Object> insert (ReplyVo vo) throws Exception {
        System.out.println("vo.toString() = " + vo.toString());
//        return CommonUtil.resultMap(service.insert(vo));

        return new HashMap<>();
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> delete (@PathVariable("id") Long id) throws Exception {
        System.out.println("id = " + id);
        return CommonUtil.resultMap(service.delete(id));
    }

    @PutMapping("/")
    public Map<String, Object> update (@RequestBody ReplyVo vo) {
        return CommonUtil.resultMap(service.update(vo));
    }
}
