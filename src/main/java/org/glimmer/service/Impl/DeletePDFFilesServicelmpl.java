package org.glimmer.service.Impl;

import org.glimmer.domain.PDFFiles;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.mapper.UserLikeFileMapper;
import org.glimmer.service.DeletePDFFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;


@Service
public class DeletePDFFilesServicelmpl implements DeletePDFFiles {

    @Autowired
    PDFFilesMapper pdfFilesMapper;
    @Autowired
    UserLikeFileMapper userLikeFileMapper;
    @Override
    public boolean DeletePDF(Long fileId) throws FileNotFoundException{

        boolean flag = false;
        PDFFiles pdfFile = pdfFilesMapper.selectById(fileId);
        if(pdfFile==null){
            return flag;
        }
        String filePath = pdfFile.getFilePath();
        File file = new File(filePath);
        //删除文件
        if(file.exists()&&file.isFile()) {
            file.delete();
        }else{
            return flag;
        }
        //删除数据库文件记录
        pdfFilesMapper.deleteById(fileId);
        userLikeFileMapper.deleteLikedFileAndUser(fileId);
        //返回管理员管理文件界面
        return true;
    }
}
