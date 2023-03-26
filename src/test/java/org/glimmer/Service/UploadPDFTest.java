package org.glimmer.Service;
import java.io.*;

import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.glimmer.domain.PDFFiles;
import org.glimmer.service.UploadPDFService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@SpringBootTest
public class UploadPDFTest {


    @Autowired
    UploadPDFService uploadPDFService;


    @Test
    void UploadTest(){


        File f1 = new File("D:\\C语言scanf的高级用法，原来scanf还有这么多新技能.pdf");
        File f2 = new File("D:\\simple database learning.pdf");
        File f3 = new File("D:\\C语言笔记2022.docx");


        try{
        FileInputStream inputStream = new FileInputStream(f1);
        MultipartFile multipartFile = new MockMultipartFile(f1.getName(), f1.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

        inputStream = new FileInputStream(f2);
            MultipartFile multipartFile1 = new MockMultipartFile(f2.getName(), f2.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);inputStream = new FileInputStream(f3);
            MultipartFile multipartFile2 = new MockMultipartFile(f3.getName(), f3.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);


        MultipartFile[] files = new MultipartFile[]{multipartFile,multipartFile1,multipartFile2};



        System.out.println(uploadPDFService.uploadPDF(files,1L));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
