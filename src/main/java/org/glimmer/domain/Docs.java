package org.glimmer.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用于存储文档内容，并记录相应pdf编号，以及文档位置.
 *
 */
@Data
@TableName("pdf_doc")
public class Docs {
    /**
     * 主键
     */
    public Long id;
    /**
     * 所属pdf编号
     */
    public String pdfId;
    /**
     * 所属页号
     */
    public String pageId;
    /**
     * 所属段落号
     */
    public String paraId;
    /**
     * 段落内容
     */
    public String content;
}
