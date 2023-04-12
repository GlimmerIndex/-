package org.glimmer.service.Impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.glimmer.config.PDFUploadConfig;
import org.glimmer.domain.PDFFiles;
import org.glimmer.domain.ResponseResult;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.service.LuceneService;
import org.glimmer.service.OCRService;
import org.glimmer.service.UploadPDFService;
import org.glimmer.utils.TessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.Tika;

import java.io.*;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;

@Service
public class UploadPDFServicelmpl implements UploadPDFService {

    @Autowired
    PDFUploadConfig PDFuploadConfig;

    @Autowired
    PDFFilesMapper PDFfilesMapper;

    @Value("${pdf-files.load-path}")
    String filePath;
    @Override
    public ResponseResult uploadPDF(MultipartFile[] pdfFiles,Long id)throws IOException{
        String hashString;
        if(pdfFiles==null||pdfFiles.length==0||pdfFiles[0].isEmpty()){
            return new ResponseResult(4009,"请选择文件");
        }
        for(MultipartFile file : pdfFiles){
            String fileName = file.getOriginalFilename();
            if(!isValidFileType(fileName,PDFuploadConfig.getAllowType())){
                return new ResponseResult(4010,file.getOriginalFilename()+"文件格式不正确，只支持"+PDFuploadConfig.getAllowType()+"格式的文件");
            }
            try {
                if(!isValidFileContent(file.getBytes(),PDFuploadConfig.getAllowType())){
                    return new ResponseResult(4011,file.getOriginalFilename()+"文件内容不正确，只支持"+PDFuploadConfig.getAllowType()+"内容的文件");
                }
            }catch(Exception e) {
                e.printStackTrace();
                return new ResponseResult(4012, "IO流出错");
            }
            // 计算文件内容的哈希值
            try{
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(file.getBytes());
                hashString = Base64.getEncoder().encodeToString(hash);
                LambdaQueryWrapper<PDFFiles> queryByHash = new LambdaQueryWrapper<PDFFiles>();
                queryByHash.eq(PDFFiles::getHashFileName,hashString);
                PDFFiles PDFExisted = PDFfilesMapper.selectOne(queryByHash);
                if(!Objects.isNull(PDFExisted)){
                    return new ResponseResult(4013,file.getOriginalFilename()+"文件已存在");
                }
            }catch(Exception e){
                e.printStackTrace();
                return new ResponseResult(4014,"文件上传失败，文件查重失败");
            }
            PDFFiles rightPDFFiles = new PDFFiles();
            rightPDFFiles.setUploadBy(id);//上传人ID
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
            rightPDFFiles.setFilePath(TessUtils.getPrefix(uniqueFileName));//文件路径
            rightPDFFiles.setHashFileName(hashString);//文件哈希串
            rightPDFFiles.setFileName(file.getOriginalFilename());//文件原始名
            rightPDFFiles.setFileSize(file.getSize());//文件大小
            rightPDFFiles.setFileType(getFileExtension(file.getOriginalFilename()));//文件类型
            Date date = new Date();
            rightPDFFiles.setUploadTime(date);//文件上传时间
            saveFile(file.getInputStream(),uniqueFileName);//保存文件
            OCRandUpdateIndex(uniqueFileName);
            try{
                PDFfilesMapper.insert(rightPDFFiles);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseResult(4015,"文件上传失败，数据库发生错误，请联系管理员");
            }
        }
        return new ResponseResult(201,"文件上传成功");
    }
    @Autowired
    OCRService ocrService;
    @Autowired
    LuceneService luceneService;
    @Async
    void OCRandUpdateIndex(String fileName) {
        try {
            ocrService.pdfToPng(fileName);
            ocrService.ocrByPath(fileName);
            luceneService.AddPdfIndex(fileName);
            System.out.println("创建索引成功："+fileName);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean isValidFileType(String fileName, List<String> allowType){
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        for (String validFileType : allowType) {
            if (validFileType.equalsIgnoreCase(fileType)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean isValidFileContent(byte[] fileBytes,List<String> allowType){
        try (InputStream is = new ByteArrayInputStream(fileBytes)) {
            String fileType = new Tika().detect(is);
            String subFileType = fileType.substring(fileType.lastIndexOf("/") + 1);
            for (String validFileType : allowType) {
                if (validFileType.equalsIgnoreCase(subFileType)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成UUID文件名
     * @param originalFilename
     * @return
     */
    public static String generateUniqueFileName(String originalFilename){
        String uuid = UUID.randomUUID().toString(); // 生成UUID
        String extension = getFileExtension(originalFilename); // 获取文件扩展名
        return uuid + "-" + originalFilename; // 拼接新文件名
    }


    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }


    /**
     * 将文件保存到本地
     * @param inputStream
     * @param fileName
     */
    private void saveFile(InputStream inputStream, String fileName) {

        OutputStream os = null;
        try {
            String path = filePath;
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
