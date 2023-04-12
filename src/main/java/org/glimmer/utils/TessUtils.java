package org.glimmer.utils;

import lombok.val;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TessUtils {

    @Bean
    public static ITesseract getTessInstance() {
        ITesseract instance  = new Tesseract();
        instance.setDatapath("tessdata");
        instance.setLanguage("chi_sim+eng"); // 中英文混合识别
        instance.setOcrEngineMode(0);
        return instance;
    }
    public static String getPrefix(String s) {
        val i = s.lastIndexOf(".");
        if(i==-1)
            return s;
        return s.substring(0,i);
    }
}
