package org.glimmer.controller;


import org.glimmer.service.DownLoadPDFService;
import org.glimmer.service.UploadPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.glimmer.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class UploadPDFController {
//TODO

  @Autowired
  UploadPDFService uploadPDFService;


  //上传PDF
    @PostMapping("/uploads/pdf/{id}")
    @PreAuthorize("hasAuthority('uploads:pdf:get')")
    public ResponseResult UploadPDF(@PathVariable Long id, @RequestBody MultipartFile[] pdfFiles)throws IOException {
        return uploadPDFService.uploadPDF(pdfFiles,id);
    }


}
