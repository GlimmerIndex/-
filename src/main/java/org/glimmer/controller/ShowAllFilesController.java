package org.glimmer.controller;


import org.glimmer.domain.PDFFiles;
import org.glimmer.domain.ResponseResult;
import org.glimmer.service.ShowAllFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowAllFilesController {

    @Autowired
    ShowAllFiles showAllFiles;

    @GetMapping("/show/pdf")
    @PreAuthorize("hasAuthority('show:pdf:see')")
    public List<PDFFiles> ShowFiles(){
        return showAllFiles.showFiles();
    }
}
