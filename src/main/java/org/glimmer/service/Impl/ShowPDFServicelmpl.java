package org.glimmer.service.Impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
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
    public ResponseEntity<StreamingResponseBody> showPDF(Long fileId){
        if (fileId == null){
            return ResponseEntity.notFound().build();
        }
        PDFFiles pdfFile = pdfFilesMapper.selectById(fileId);
        if(pdfFile == null){
            return ResponseEntity.notFound().build();
        }
        String filePath = pdfFile.getFilePath();
        File file = new File(filePath);
        if (file == null&&file.canRead() == false){
            return ResponseEntity.notFound().build();
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline",pdfFile.getFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream -> {
                        try  {InputStream inputStream = new FileInputStream(file);
                            StreamUtils.copy(inputStream,outputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("输出流错误");
                        }
                    });
        }catch (Exception exception){
            exception.printStackTrace();
             return ResponseEntity.noContent().build();
        }

    }






    public String getFilePreName(String fileName){
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(0,dotIndex-1);
    }
}
