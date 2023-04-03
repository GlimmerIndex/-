package org.glimmer.controller;


import org.glimmer.service.DeletePDFFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeletePDFFilesController {

    @Autowired
    DeletePDFFiles deletePDFFiles;
    @GetMapping("/delete/pdf")
    @PreAuthorize("hasAuthority('delete:pdf:throw')")
    public String DeletePDFs(@RequestParam("fileId") Long fileId)throws Exception{
        return deletePDFFiles.DeletePDF(fileId);
    }
}
