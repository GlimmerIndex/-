package org.glimmer.service;

import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadPDFService  {


    /**
     * 上传PDF文件
     * @param PDFFiles
     * @return
     */
    public ResponseResult uploadPDF(MultipartFile[] PDFFiles, Long id) throws IOException;

    /**
     * 判断PDF文件格式是否正确
     * @param fileName
     * @return
     */
    public boolean isValidFileType(String fileName, List<String> allowType);

    /**
     * 判断文件内容是否正确
     * @param fileBytes
     * @return
     */
    public boolean isValidFileContent(byte[] fileBytes,List<String> allowType);



}
