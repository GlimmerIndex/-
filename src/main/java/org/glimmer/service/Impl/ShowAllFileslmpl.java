package org.glimmer.service.Impl;

import org.glimmer.domain.PDFFiles;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.ShowAllFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowAllFileslmpl implements ShowAllFiles {

    @Autowired
    PDFFilesMapper pdfFilesMapper;

    @Override
    public List<PDFFiles> showFiles(){
        List<PDFFiles> pdfFiles = pdfFilesMapper.findAll();
        if(pdfFiles.isEmpty()){
            return null;
        }
        return pdfFiles;
    }

}
