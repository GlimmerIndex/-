package org.glimmer.controller;


import io.jsonwebtoken.Claims;
import org.glimmer.service.DownLoadPDFService;
import org.glimmer.service.UploadPDFService;
import org.glimmer.utils.JwtUtil;
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


  @Autowired
  UploadPDFService uploadPDFService;


  //上传PDF
    @PostMapping("/uploads/pdf")
    @PreAuthorize("hasAuthority('uploads:pdf:get')")
    public ResponseResult UploadPDF(@RequestHeader("token") String token, @RequestBody MultipartFile[] pdfFiles)throws IOException {
      try {
        Claims claims = JwtUtil.parseJWT(token);
        Long userId = Long.valueOf(claims.getSubject());
        return uploadPDFService.uploadPDF(pdfFiles,userId);
      }catch (Exception e){
        e.printStackTrace();
        return new ResponseResult<>(4025,"token 非法");
      }

    }


}
