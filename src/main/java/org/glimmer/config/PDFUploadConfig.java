package org.glimmer.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("storage.image")
@Data
public class PDFUploadConfig {
    /**
     * 允许的文件类型
     */
    private List<String> allowType;
}
