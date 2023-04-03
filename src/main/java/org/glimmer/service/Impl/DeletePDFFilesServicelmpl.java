package org.glimmer.service.Impl;

import org.glimmer.domain.PDFFiles;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.DeletePDFFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;


@Service
public class DeletePDFFilesServicelmpl implements DeletePDFFiles {

    @Autowired
    PDFFilesMapper pdfFilesMapper;
    @Override
    public String DeletePDF(Long fileId) throws FileNotFoundException{


        PDFFiles pdfFile = pdfFilesMapper.selectById(fileId);
        if(pdfFile==null){
            return "文件不存在";
        }
        String filePath = pdfFile.getFilePath();
        File file = new File(filePath);
        //删除文件
        if(file.exists()) {
            file.delete();
        }else{
            return "文件不存在";
        }
        //删除数据库文件记录
        pdfFilesMapper.deleteById(fileId);
        //返回管理员管理文件界面
        return "OK";//TODO 重定向至admin管理文件页面
    }
}
