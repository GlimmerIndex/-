package org.glimmer.controller;


import org.glimmer.service.ShowPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
public class ShowPDFController {


    @Autowired
    ShowPDFService showPDFService;

    @GetMapping("/preview/pdf/{fileId}")
    @PreAuthorize("hasAuthority('show:pdf:preview')")
    public ResponseEntity<StreamingResponseBody> showPDF(@PathVariable Long fileId){
        return showPDFService.showPDF(fileId);
    }
}
