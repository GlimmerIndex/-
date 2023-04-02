package org.glimmer.service;

import java.io.FileNotFoundException;

public interface DeletePDFFiles {
    /**
     * 删除文件
     * @param fileId
     * @return admin管理文件界面
     * @throws FileNotFoundException
     */
    public String DeletePDF(Long fileId) throws FileNotFoundException;
}
