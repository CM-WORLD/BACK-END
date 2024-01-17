package com.cms.world.controller;


import com.cms.world.domain.dto.PostDto;
import com.cms.world.domain.vo.PostVo;
import com.cms.world.service.PostService;
import com.cms.world.utils.CommonUtil;
import com.cms.world.utils.GlobalStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController { // 블로그 이미지 리스트

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> add(PostVo vo) throws IOException {
        try {
            service.add(vo, "/post");
            return ResponseEntity.ok().body(GlobalStatus.SUCCESS.getMsg());
        } catch (IOException e) {
            return ResponseEntity.status(GlobalStatus.INTERNAL_SERVER_ERR.getStatus()).body(GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
    }

    /* 타입별 일러스트 조회 */
    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(name = "type") String type) {
        return CommonUtil.renderResultByMap(service.list(type));
    }
}
