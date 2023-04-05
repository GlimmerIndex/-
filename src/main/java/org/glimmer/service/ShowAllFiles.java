package org.glimmer.service;

import org.glimmer.domain.PDFFiles;
import org.springframework.ui.Model;

import java.util.List;

public interface ShowAllFiles {
    /**
     * 展示文件
     * @return 所有文件信息
     */
    public List<PDFFiles> showFiles();
}
