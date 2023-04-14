package org.glimmer.Service;

import lombok.val;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.glimmer.service.OCRService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class OCRTest {
    @Autowired
    OCRService ocrService;

    @Test
    public void imgTest() throws IOException, InterruptedException {
        ocrService.pdfToPng("d0528bc8-f16f-4af1-a5bd-f15b02d08260-《维多利亚3》经济学与国家经济浅论.pdf");
    }

    @Autowired
    ITesseract tesseract;

    @Test
    public void ocrTest()  {
        ocrService.ocrByPath("d0528bc8-f16f-4af1-a5bd-f15b02d08260-《维多利亚3》经济学与国家经济浅论");
        // tesseract.doOCR()
    }
}
