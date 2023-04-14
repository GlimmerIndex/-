package org.glimmer.service.Impl;

import java.io.*;
import org.glimmer.domain.PDFFiles;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.ShowPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


@Service
public class ShowPDFServicelmpl implements ShowPDFService {

    @Autowired
    PDFFilesMapper pdfFilesMapper;


    @Override
    public String showPDF(Long fileId){
        if (fileId == null){
            return null;
        }
        PDFFiles pdfFile = pdfFilesMapper.selectById(fileId);
        if(pdfFile == null){
            return null;
        }
        String filePath = pdfFile.getFilePath();
        File file = new File(filePath);
        if (file == null&&file.canRead() == false){
            return null;
        }
        return filePath;
    }






    public String getFilePreName(String fileName){
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(0,dotIndex-1);
    }
}
