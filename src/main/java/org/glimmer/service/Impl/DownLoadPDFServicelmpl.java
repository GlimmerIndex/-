package org.glimmer.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.glimmer.domain.PDFFiles;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.DownLoadPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class DownLoadPDFServicelmpl implements DownLoadPDFService {


    @Autowired
    PDFFilesMapper pdfFilesMapper;
    @Override
    public ResponseEntity<StreamingResponseBody> DownLoadPDF(Long fileId){


        PDFFiles pdfFiles = pdfFilesMapper.selectById(fileId);
        //文件不存在

        if(pdfFiles == null){
            return ResponseEntity.notFound().build();
        }


        try {
            String filePath = pdfFiles.getFilePath();
            File file = new File(filePath);
            if (file == null&&file.canRead()==false){
                throw new RuntimeException("Not found");
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFiles.getFileName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputStream -> {
                        try  {InputStream inputStream = new FileInputStream(file);
                            StreamUtils.copy(inputStream, outputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("输出流错误");
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("文件下载出错");
            return ResponseEntity.noContent().build();
        }
    }
}
