package org.glimmer.controller;

import org.glimmer.domain.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PDFController {

    @GetMapping("/uploads/pdf/{id}")
    @PreAuthorize("hasAuthority('uploads:pdf:get')")
    public ResponseResult GetPDF(@PathVariable int id) {
        return new ResponseResult<>(200,"开始下载pdf:"+id);
    }
}
