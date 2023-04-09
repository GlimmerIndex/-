package org.glimmer.controller;


import org.glimmer.domain.PDFFiles;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.DownLoadPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
public class DownloadPDFController {

   @Autowired
    DownLoadPDFService downLoadPDFService;
    @Autowired
    private PDFFilesMapper pdffilesMapper;
    @GetMapping(value = "/download/pdf",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @PreAuthorize("hasAuthority('download:pdf:catch')")
    public ResponseEntity<StreamingResponseBody> DownloadPDF(@RequestParam("fileId") Long fileId)throws Exception{
        return downLoadPDFService.DownLoadPDF(fileId);
    }
}
