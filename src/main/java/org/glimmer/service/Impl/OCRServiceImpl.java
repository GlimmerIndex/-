package org.glimmer.service.Impl;

import lombok.AllArgsConstructor;
import lombok.val;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.glimmer.domain.Docs;
import org.glimmer.mapper.DocsMapper;
import org.glimmer.service.OCRService;
import org.glimmer.utils.TessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

@Service
public class OCRServiceImpl implements OCRService {
    @Value("${pdf-files.load-path}")
    String filePath;
    @Value("${python-script-path}")
    String scriptPath;
    @Value("${python-ocr-script-name}")
    String scriptName;
    @Value("${python-ocr-img-name}")
    String imgPath;

    @Autowired
    DocsMapper docsMapper;
    @Autowired
    ITesseract tesseract;

    /**
     * @param fileName
     */
    @Override
    public void pdfToPng(String fileName) throws IOException, InterruptedException {
        Process proc;
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath + '/' + scriptName, filePath, fileName, imgPath);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);
    }

    /**
     * @param fileName
     * @return
     */
    @Override
    public void ocrByPath(String fileName) {

        File fileImgDir = new File(TessUtils.getPrefix(imgPath+"/"+fileName));
        if(Objects.isNull(fileImgDir.listFiles()))
        {
            return;
        }
//        int cnt = Objects.requireNonNull(fileImgDir.listFiles()).length;
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(cnt+1);
        for(File file : Objects.requireNonNull(fileImgDir.listFiles())) {
             System.out.println("Start OCR file "+ file.getPath());
            try {
                String content = tesseract.doOCR(file);
                //  System.out.println(content);
                Docs doc = new Docs();
                doc.setContent(content);
                doc.setParaId("1");
                doc.setPdfId(TessUtils.getPrefix(TessUtils.getPrefix(fileName)));
                doc.setPageId(file.getName());
                val insert = docsMapper.insert(doc);
                // System.out.println(insert);
            } catch (TesseractException e) {
                e.printStackTrace();
            }
        }
    }
    @AllArgsConstructor
    private class ocrOnePageThread extends Thread {
        private String filePath;
        private CyclicBarrier cyclicBarrier;

        public void run() {
            File file = new File(filePath);
            System.out.println("Start OCR file "+file.getPath());

            try {
                String content = tesseract.doOCR(file);
                // System.out.println(content);
                cyclicBarrier.await();
            } catch (TesseractException | BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
