package com.cms.world.post;


import com.cms.world.common.util.CommonUtil;
import com.cms.world.common.code.GlobalStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/post")
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
