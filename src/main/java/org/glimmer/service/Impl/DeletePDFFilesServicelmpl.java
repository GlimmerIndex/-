package org.glimmer.service.Impl;

import org.glimmer.domain.PDFFiles;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.DeletePDFFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;


@Service
public class DeletePDFFilesServicelmpl implements DeletePDFFiles {

    @Autowired
    PDFFilesMapper pdfFilesMapper;
    @Override
    public String DeletePDF(Long fileId) throws FileNotFoundException{
        PDFFiles pdfFile = pdfFilesMapper.selectById(fileId);
        String filePath = "";
            return "hello";
    }
}
