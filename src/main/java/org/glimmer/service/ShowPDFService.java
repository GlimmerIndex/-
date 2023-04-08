package org.glimmer.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;

public interface ShowPDFService {


    /**
     * 返回HTML的文件
     * @param fileId
     * @return
     */
    public ResponseEntity<StreamingResponseBody> showPDF(Long fileId);



}
