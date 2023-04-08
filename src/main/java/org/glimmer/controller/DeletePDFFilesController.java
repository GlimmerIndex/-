package org.glimmer.controller;


import org.glimmer.domain.ResponseResult;
import org.glimmer.service.DeletePDFFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeletePDFFilesController {

    @Autowired
    DeletePDFFiles deletePDFFiles;
    @GetMapping("/delete/pdf")
    @PreAuthorize("hasAuthority('delete:pdf:throw')")
    public ResponseResult DeletePDFs(@RequestParam("fileId") Long fileId){
        try{
            if (deletePDFFiles.DeletePDF(fileId)==true){
                return new ResponseResult<>(4025,"文件删除成功");
            }else{
               return new ResponseResult<>(4026,"文件删除失败");
            }
        }catch (Exception e){
            return new ResponseResult<>(4027,"文件不存在");
        }

    }
}
