package org.glimmer.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface DownLoadPDFService {


    /**
     * 下载PDF文件
     * @param fileId
     * @return
     */
    public ResponseEntity<StreamingResponseBody> DownLoadPDF(Long fileId);
}
