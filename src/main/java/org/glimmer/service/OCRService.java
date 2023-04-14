package org.glimmer.service;

import org.glimmer.domain.Docs;

import java.io.IOException;
import java.util.List;

public interface OCRService {
    /**
     * 给定PDF名称，将其转化为一组图片。
     * @param fileName
     * @throws IOException
     * @throws InterruptedException
     */
    public void pdfToPng(String fileName) throws IOException, InterruptedException;

    /**
     * 给定名称，将其识别文字，并插入数据库
     * @param fileName
     */
    public void ocrByPath(String fileName);
}
