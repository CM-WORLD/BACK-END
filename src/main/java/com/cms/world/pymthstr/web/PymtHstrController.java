package com.cms.world.pymthstr.web;

import com.cms.world.pymthstr.service.PymtHstrService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pymt/hstr")
@AllArgsConstructor
public class PymtHstrController {

    private final PymtHstrService service;

    @PostMapping("/upload/excel")
    public ResponseEntity excelUpload (MultipartFile file) throws Exception {
        service.uploadExcel(file);
        //
        return ResponseEntity.ok("success");
    }
}
