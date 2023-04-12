package org.glimmer.controller;


import org.glimmer.domain.ResponseResult;
import org.glimmer.service.ShowPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ShowPDFController {


    @Autowired
    ShowPDFService showPDFService;

    @GetMapping("/preview/pdf/{fileId}")
    @PreAuthorize("hasAuthority('show:pdf:preview')")
    public ResponseResult showPDF(@PathVariable Long fileId){
        String pdfURL = showPDFService.showPDF(fileId);
        if (pdfURL != null){
            return new ResponseResult(4028,pdfURL);
        }else {
            return new ResponseResult<>(4029,"文件未找到");
        }
    }
}
